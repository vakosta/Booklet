package me.annenkov.julistaandroid.domain.model.mos

import me.annenkov.julistaandroid.data.model.booklet.journal.MarksItem

class ScheduleResponse(val date: String,
                       val beginTime: List<Int>,
                       val endTime: List<Int>,
                       val dayNumber: Int,
                       val lessonNumber: Int,
                       val subject: String,
                       val topic: String,
                       val comment: String,
                       val marks: List<MarksItem?>,
                       var homework: HomeworkResponse?)
