package me.annenkov.julistaandroid.domain

import com.google.gson.JsonParseException
import me.annenkov.julistaandroid.data.JulistaApi
import me.annenkov.julistaandroid.data.model.mos.profile.Profile
import me.annenkov.julistaandroid.data.model.mos.schedule.HomeworkToVerify
import me.annenkov.julistaandroid.domain.model.mos.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object MosApiHelper {
    private var retrofit: Retrofit? = null

    fun getAPI(): JulistaApi {
        val BASE_URL = "https://dnevnik.mos.ru"

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        return retrofit!!.create<JulistaApi>(JulistaApi::class.java)
    }

    @Throws(IOException::class, JsonParseException::class)
    fun getProfile(token: String, pid: Int?, studentProfileId: Int): Profile {
        val response = getAPI()
                .getProfile(token, pid!!, studentProfileId, 6, true, true)
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
            groupsParamBuilder.append(String.format("%s,", group.getId()))
        }
        groupsParamBuilder.deleteCharAt(groupsParamBuilder.length - 1)
        val groupsParam = groupsParamBuilder.toString()

        val response = getAPI()
                .getSchedule(token, pid!!, studentProfileId, formatFrom, formatTo, 6, groupsParam)
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
                            scheduleResponse.marks.addAll(mark.marks)
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
        val response = getAPI()
                .getMarks(token, pid!!, studentProfileId, createdAtFrom, createdAtTo, 6, 1, 50)
                .execute()

        if (response.body() != null) {
            val marks = response.body()
            val marksResponse = arrayListOf<MarkResponse>()

            if (marks != null) {
                for (mark in marks) {
                    val markResponse = MarkResponse(mark.getScheduleLessonId(), mark.getDate(), "", ArrayList())
                    for (value in mark.getValues()) {
                        markResponse.getMarks().add(value.getGrade().getFive().toInt())
                    }
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
        val response = getAPI()
                .getHomework(token, pid!!, studentProfileId, beginDate, endDate, 6, 1, 50)
                .execute()

        if (response.body() != null) {
            val homeworkBases = response.body()
            val homeworks = arrayListOf<HomeworkResponse>()

            if (homeworkBases != null) {
                for (hw in homeworkBases) {
                    val homeworkResponse = HomeworkResponse(hw.getHomeworkEntry().getHomeworkId(),
                            hw.getHomeworkEntry().getHomework().getDatePreparedFor(),
                            hw.getHomeworkEntry().getHomework().getSubject().getName(),
                            hw.getHomeworkEntry().getDescription(),
                            ArrayList())
                    for (attachment in hw.getHomeworkEntry().getAttachments()) {
                        homeworkResponse.getAttachments().add(attachment.getPath())
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
        val response = getAPI()
                .getProgress(token, pid!!, studentProfileId, 6)
                .execute()

        if (response.body() != null) {
            val result = response.body()
            val progresses = arrayListOf<ProgressResponse>()

            if (result != null) {
                for (progress in result) {
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

            return progresses
        }

        throw JsonParseException("Wrong JSON object.")
    }
}