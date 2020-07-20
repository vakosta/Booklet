package com.booklet.bookletandroid.domain.model

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class Date(var day: Int, var month: Int, var year: Int) {
    val monthText: String
        get() {
            return when (month) {
                0 -> "Январь"
                1 -> "Февраль"
                2 -> "Март"
                3 -> "Апрель"
                4 -> "Май"
                5 -> "Июнь"
                6 -> "Июль"
                7 -> "Август"
                8 -> "Сентябрь"
                9 -> "Октябрь"
                10 -> "Ноябрь"
                else -> "Декабрь"
            }
        }

    constructor() : this(0, 0, 0) {
        val date = Calendar.getInstance().toDate()

        day = date.day
        month = date.month
        year = date.year
    }

    fun getDayOfWeekNumber(): Int = this.toCalendar().get(Calendar.DAY_OF_WEEK)

    fun getDayOfWeek(): String {
        return when (getDayOfWeekNumber()) {
            0 -> DayOfWeek.MONDAY.text
            1 -> DayOfWeek.TUESDAY.text
            2 -> DayOfWeek.WEDNESDAY.text
            3 -> DayOfWeek.THURSDAY.text
            4 -> DayOfWeek.FRIDAY.text
            5 -> DayOfWeek.SATURDAY.text
            else -> DayOfWeek.SUNDAY.text
        }
    }

    fun toCalendar(): Calendar {
        val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                .parse("$day.$month.$year")
        val calendar = GregorianCalendar()
        calendar.time = date!!

        return calendar
    }

    /**
     * Прибавляет к текущей дате определённое количество дней.
     *
     * @param days это количество дней, которое прибавляется к дате.
     */
    operator fun plus(days: Int): Date {
        val calendar = this.toCalendar()
        calendar.add(Calendar.DATE, days)
        return calendar.toDate()
    }

    /**
     * Вычитает из текущей даты определённое количество дней.
     *
     * @param days это количество дней, которое вычитается из даты.
     */
    operator fun minus(days: Int): Date {
        return plus(-days)
    }

    /**
     * Находит количество дней между двумя датами.
     *
     * @param date это вторая дата, с которой требуется найти разницу в днях.
     */
    operator fun minus(date: Date): Int {
        val start = this.toCalendar().timeInMillis
        val end = date.toCalendar().timeInMillis
        return TimeUnit.MILLISECONDS.toDays(abs(end - start)).toInt()
    }

    override fun toString(): String = "$day.$month.$year"

    companion object {
        fun Calendar.toDate(): Date {
            return Date(this[Calendar.DAY_OF_MONTH],
                    this[Calendar.MONTH],
                    this[Calendar.YEAR])
        }

        fun String.toDate(): Date {
            val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    .parse(this)
            val calendar = GregorianCalendar()
            calendar.time = date!!

            return calendar.toDate()
        }
    }

    enum class DayOfWeek(val number: Int, val text: String) {
        MONDAY(1, "ПН"),
        TUESDAY(2, "ВТ"),
        WEDNESDAY(3, "СР"),
        THURSDAY(4, "ЧТ"),
        FRIDAY(5, "ПТ"),
        SATURDAY(6, "СБ"),
        SUNDAY(7, "ВС")
    }
}