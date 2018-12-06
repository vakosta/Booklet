package me.annenkov.julistaandroid.domain.model.mos

class PeriodResponse(val name: String,
                     val avgFive: Float,
                     val avgHundred: Float,
                     val finalMark: String?,
                     val start: String,
                     val end: String,
                     val marks: List<Int>)
