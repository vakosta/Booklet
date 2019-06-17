package me.annenkov.julistaandroid.domain

import android.content.Context
import android.util.Log
import com.google.gson.JsonParseException
import me.annenkov.julistaandroid.data.JulistaApi
import me.annenkov.julistaandroid.data.model.booklet.Auth
import me.annenkov.julistaandroid.data.model.booklet.students.Students
import me.annenkov.julistaandroid.data.model.julista.ResultCheckNotificationsSubscription
import me.annenkov.julistaandroid.data.model.julista.ResultSetNotificationsSubscription
import me.annenkov.julistaandroid.data.model.julista.account.Account
import me.annenkov.julistaandroid.data.model.mos.profile.Profile
import me.annenkov.julistaandroid.domain.model.mos.HomeworkResponse
import me.annenkov.julistaandroid.domain.model.mos.PeriodResponse
import me.annenkov.julistaandroid.domain.model.mos.ProgressResponse
import me.annenkov.julistaandroid.domain.model.mos.ScheduleResponse
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
                    || prefs.userSecret!!.isEmpty()) {
                val request = getAPI(ApiType.JULISTA)
                        .auth("mosru", login, password).execute().body()
                prefs.userTokenLastUpdate = currentTime
                prefs.userPid = request?.id
                prefs.userSecret = request?.secret
                request
            } else {
                val auth = Auth()
                auth.status = true
                auth.message = "Ok"
                auth.id = 123
                auth.secret = prefs.userSecret
                auth.created = false
                auth
            }
            return token ?: Auth()
        }
    }

    fun getStudents(id: Long, secret: String): Call<Students> {
        return getAPI(ApiType.JULISTA).getStudents(id, secret)
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
    fun getSchedule(pid: Long,
                    secret: String?,
                    from: String,
                    to: String): List<ScheduleResponse> {
        val response = getAPI(ApiType.JULISTA)
                .getSchedule(pid,
                        secret!!,
                        from,
                        to)
                .execute()

        if (response.body() != null) {
            val days = response.body()!!.data!!.days
            val scheduleResponses = arrayListOf<ScheduleResponse>()

            for (day in days!!) {
                val subjects = day?.subjects

                for (subject in subjects!!) {
                    val scheduleResponse = ScheduleResponse(
                            day.date!!,
                            subject!!.time!![0]!!.split(":").map { it.toInt() },
                            subject.time!![1]!!.split(":").map { it.toInt() },
                            1,
                            subject.number!!,
                            subject.name!!,
                            subject.label!!.title!!,
                            "",
                            subject.marks ?: arrayListOf(),
                            HomeworkResponse(1,
                                    "kek",
                                    "ksdmfk",
                                    "sdfkm",
                                    arrayListOf<String>())
                    )
                    scheduleResponses.add(scheduleResponse)
                }
            }

            return scheduleResponses
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
                            progresses.add(ProgressResponse(progress.subjectName!!,
                                    progress.avgFive!!.toFloat(),
                                    progress.avgHundred!!.toFloat(),
                                    periods,
                                    progress.final?.toInt()))
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
        private const val JULISTA_URL = "http://bklet.ml/api/"
        private const val MOS_URL = "https://dnevnik.mos.ru/"

        private const val CACHE_SIZE = (1 * 1024 * 1024).toLong()
    }
}