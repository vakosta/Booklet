package me.annenkov.julistaandroid.domain.model.mos

class PeriodResponse(val name: String,
                     val avgFive: Float,
                     val avgHundred: Float,
                     val finalMark: Int?,
                     val start: String,
                     val end: String,
                     val marks: List<Int>)
