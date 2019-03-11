package me.annenkov.julistaandroid.domain

import android.content.Context
import android.util.Log
import com.google.gson.JsonParseException
import me.annenkov.julistaandroid.data.JulistaApi
import me.annenkov.julistaandroid.data.model.julista.ResultCheckNotificationsSubscription
import me.annenkov.julistaandroid.data.model.julista.ResultSetNotificationsSubscription
import me.annenkov.julistaandroid.data.model.julista.account.Account
import me.annenkov.julistaandroid.data.model.julista.auth.Auth
import me.annenkov.julistaandroid.data.model.mos.profile.Profile
import me.annenkov.julistaandroid.data.model.mos.schedule.HomeworkToVerify
import me.annenkov.julistaandroid.domain.model.mos.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException

class ApiHelper private constructor(val context: Context) {
    private var retrofit: Retrofit? = null
    private var retrofitMos: Retrofit? = null

    fun deleteCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        } else return if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }

    private val rewriteResponseInterceptor = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        if (cacheControl == null
                || cacheControl.contains("no-store")
                || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate")
                || cacheControl.contains("max-age=0")) {
            originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=0")
                    .build()
        } else {
            originalResponse
        }
    }

    private val rewriteResponseInterceptorOffline = Interceptor { chain ->
        var request = chain.request()
        if (!hasNetwork()) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale="
                            + 60 * 60 * 24 * 7)
                    .build()
        }
        chain.proceed(request)
    }

    init {
        if (!Preferences.getInstance(context).clearedCache) {
            deleteCache(context)
            Preferences.getInstance(context).clearedCache = true
        }
        val cache = Cache(context.cacheDir, CACHE_SIZE)
        val client = OkHttpClient().newBuilder()
                .cache(cache)
                .addNetworkInterceptor(rewriteResponseInterceptor)
                .addInterceptor(rewriteResponseInterceptorOffline)
                .build()
        val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)

        retrofit = retrofitBuilder
                .baseUrl(JULISTA_URL)
                .build()
        retrofitMos = retrofitBuilder
                .baseUrl(MOS_URL)
                .build()
    }

    private fun getAPI(apiType: ApiType): JulistaApi {
        return when (apiType) {
            ApiType.JULISTA -> retrofit!!.create<JulistaApi>(JulistaApi::class.java)
            ApiType.MOS -> retrofitMos!!.create<JulistaApi>(JulistaApi::class.java)
        }
    }

    fun hasNetwork(): Boolean {
        try {
            val e = context.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
            val activeNetwork = e.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } catch (e: Exception) {
            Log.w("API", e.toString())
        }
        return false
    }

    fun auth(login: String, password: String, fcmToken: String?, inviteCode: String?): Auth {
        synchronized(this) {
            val prefs = Preferences.getInstance(context)
            val currentTime = System.currentTimeMillis() / 1000
            val token = if (currentTime - prefs.userTokenLastUpdate > 5 // TODO: Исправить баг
                    || prefs.userToken.isEmpty()) {
                val request = getAPI(ApiType.JULISTA)
                        .auth(login, password, fcmToken, inviteCode).execute().body()
                prefs.userTokenLastUpdate = currentTime
                prefs.userToken = request?.token ?: ""
                request
            } else {
                val auth = Auth()
                auth.token = prefs.userToken
                auth.pid = prefs.userPid
                auth.botCode = prefs.botCode
                auth.studentProfileId = prefs.userStudentProfileId
                auth.students = prefs.userStudentProfiles
                auth
            }
            return token ?: Auth()
        }
    }

    fun getAccount(token: String, pid: String): Call<Account> {
        return getAPI(ApiType.JULISTA).getAccount(token, pid)
    }

    fun checkNotificationsSubscription(pid: String): Call<ResultCheckNotificationsSubscription> {
        return getAPI(ApiType.JULISTA).checkNotificationsSubscription(pid)
    }

    fun setNotificationsSubscription(pid: String, token: String, hash: String)
            : Call<ResultSetNotificationsSubscription> {
        return getAPI(ApiType.JULISTA).setNotificationsSubscription(pid, token, hash)
    }

    @Throws(IOException::class, JsonParseException::class)
    fun getProfile(token: String, pid: Int?, studentProfileId: Int): Profile {
        val response = getAPI(ApiType.MOS)
                .getProfile(token,
                        pid!!,
                        studentProfileId,
                        6,
                        true,
                        true)
                .execute()

        if (response.body() != null) {
            return response.body()!!
        }

        throw JsonParseException("Wrong JSON object.")
    }

    @Throws(IOException::class, JsonParseException::class)
    fun getSchedule(token: String,
                    pid: Int?,
                    studentProfileId: Int,
                    from: String,
                    to: String): List<ScheduleResponse> {
        val fromArray = from.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val formatFrom = String.format("%s-%s-%s", fromArray[2], fromArray[1], fromArray[0])

        val toArray = to.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val formatTo = String.format("%s-%s-%s", toArray[2], toArray[1], toArray[0])

        val groups = getProfile(token, pid, studentProfileId).groups
        val groupsParamBuilder = StringBuilder()
        for (group in groups) {
            groupsParamBuilder.append(String.format("%s,", group.id))
        }
        groupsParamBuilder.deleteCharAt(groupsParamBuilder.length - 1)
        val groupsParam = groupsParamBuilder.toString()

        val response = getAPI(ApiType.MOS)
                .getSchedule(token,
                        pid!!,
                        studentProfileId,
                        formatFrom,
                        formatTo,
                        6,
                        groupsParam,
                        true,
                        true)
                .execute()

        if (response.body() != null) {
            val scheduleItems = response.body()
            val scheduleResponses = arrayListOf<ScheduleResponse>()
            val marks = getMarks(token, pid, studentProfileId, from, to)
            val homeworks = getHomework(token, pid, studentProfileId, from, to)

            if (scheduleItems != null) {
                for (scheduleItem in scheduleItems) {
                    val scheduleResponse = ScheduleResponse(String.format("%s.%s.%s",
                            scheduleItem.date!![0],
                            scheduleItem.date!![1],
                            scheduleItem.date!![2]),
                            scheduleItem.time ?: arrayListOf(0, 0),
                            DateHelper.getEndTime(scheduleItem.time as MutableList<Int>),
                            scheduleItem.dayNumber ?: 0,
                            scheduleItem.lessonNumber ?: 0,
                            scheduleItem.subjectName ?: "",
                            scheduleItem.topicName ?: "",
                            scheduleItem.comment ?: "",
                            ArrayList(),
                            HomeworkResponse(0, "", "", "", ArrayList()))

                    val markIterator = marks.iterator()
                    while (markIterator.hasNext()) {
                        val mark = markIterator.next()
                        if (mark.scheduleLessonId == scheduleItem.id) {
                            scheduleResponse.marks.add(mark)
                            markIterator.remove()
                        }
                    }

                    val homeworkIterator = homeworks.iterator()
                    while (homeworkIterator.hasNext()) {
                        val homework = homeworkIterator.next()
                        val homeworkToVerify = HomeworkToVerify()
                        homeworkToVerify.id = homework.id
                        if (scheduleItem.homeworksToVerify?.contains(homeworkToVerify)
                                        ?: continue) {
                            scheduleResponse.homework = homework
                            homeworkIterator.remove()
                            break
                        }
                    }

                    scheduleResponses.add(scheduleResponse)
                }
            }

            scheduleResponses.sortWith(Comparator { o1, o2 ->
                val sComp = o1.dayNumber - o2.dayNumber

                if (sComp != 0) {
                    return@Comparator sComp
                }

                o1.lessonNumber - o2.lessonNumber
            })

            return scheduleResponses
        }

        throw JsonParseException("Wrong JSON object.")
    }

    @Throws(IOException::class, JsonParseException::class)
    fun getMarks(token: String,
                 pid: Int?,
                 studentProfileId: Int,
                 createdAtFrom: String,
                 createdAtTo: String): MutableList<MarkResponse> {
        val response = getAPI(ApiType.MOS)
                .getMarks(token,
                        pid!!,
                        studentProfileId,
                        createdAtFrom,
                        createdAtTo,
                        6,
                        1,
                        50)
                .execute()

        if (response.body() != null) {
            val marks = response.body()
            val marksResponse = arrayListOf<MarkResponse>()

            if (marks != null) {
                for (mark in marks) {
                    val markResponse = MarkResponse(mark.scheduleLessonId,
                            mark.date,
                            "",
                            mark.values[0].grade.five.toInt(),
                            mark.isExam,
                            mark.isPoint,
                            mark.weight)
                    marksResponse.add(markResponse)
                }
            }

            return marksResponse
        }

        throw JsonParseException("Wrong JSON object.")
    }

    @Throws(IOException::class, JsonParseException::class)
    fun getHomework(token: String,
                    pid: Int?,
                    studentProfileId: Int,
                    beginDate: String,
                    endDate: String): MutableList<HomeworkResponse> {
        val response = getAPI(ApiType.MOS)
                .getHomework(token,
                        pid!!,
                        studentProfileId,
                        beginDate,
                        endDate,
                        6,
                        1,
                        50)
                .execute()

        if (response.body() != null) {
            val homeworkBases = response.body()
            val homeworks = arrayListOf<HomeworkResponse>()

            if (homeworkBases != null) {
                for (hw in homeworkBases) {
                    val homeworkResponse = HomeworkResponse(hw.homeworkEntry.homeworkId,
                            hw.homeworkEntry.homework.datePreparedFor,
                            hw.homeworkEntry.homework.subject.name,
                            hw.homeworkEntry.description,
                            ArrayList())
                    for (attachment in hw.homeworkEntry.attachments) {
                        homeworkResponse.attachments.add(attachment.path)
                    }
                    homeworks.add(homeworkResponse)
                }
            }

            return homeworks
        }

        throw JsonParseException("Wrong JSON object.")
    }

    @Throws(IOException::class, JsonParseException::class)
    fun getProgress(token: String,
                    pid: Int?, studentProfileId: Int): List<ProgressResponse> {
        val response = getAPI(ApiType.MOS)
                .getProgress(token, pid!!, studentProfileId, 6)
                .execute()

        if (response.body() != null) {
            val result = response.body()
            val progresses = arrayListOf<ProgressResponse>()

            if (result != null) {
                for (progress in result) {
                    if (progress.avgFive != 0.0) {
                        val periods = arrayListOf<PeriodResponse>()
                        for (period in progress.periods!!) {
                            val marks = arrayListOf<Int>()

                            for (mark in period.marks!!) {
                                marks.add(mark.values[0].five.toInt())
                            }

                            try {
                                periods.add(PeriodResponse(period.name!!,
                                        period.avgFive!!.toFloat(),
                                        period.avgHundred!!.toFloat(),
                                        period.finalMark,
                                        period.start!!,
                                        period.end!!,
                                        marks))
                            } catch (ignored: NumberFormatException) {
                            }
                        }

                        try {
                            progresses.add(ProgressResponse(progress.subjectName,
                                    progress.avgFive!!.toFloat(),
                                    progress.avgHundred!!.toFloat(),
                                    periods))
                        } catch (ignored: NumberFormatException) {
                        }
                    }
                }
            }

            return progresses
        }

        throw JsonParseException("Wrong JSON object.")
    }

    enum class ApiType {
        JULISTA,
        MOS,
    }

    companion object : SingletonHolder<ApiHelper, Context>(::ApiHelper) {
        private const val JULISTA_URL = "https://api.julista.org/"
        private const val MOS_URL = "https://dnevnik.mos.ru/"

        private const val CACHE_SIZE = (1 * 1024 * 1024).toLong()
    }
}