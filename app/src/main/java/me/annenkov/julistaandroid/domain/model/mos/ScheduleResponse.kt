package me.annenkov.julistaandroid.domain.model.mos

class ScheduleResponse(val date: String,
                       val beginTime: List<Int>,
                       val endTime: List<Int>,
                       val dayNumber: Int,
                       val lessonNumber: Int,
                       val subject: String,
                       val topic: String,
                       val comment: String,
                       val marks: ArrayList<Int>,
                       var homework: HomeworkResponse?)
