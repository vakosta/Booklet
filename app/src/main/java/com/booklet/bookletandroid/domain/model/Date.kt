package com.booklet.bookletandroid.domain.model

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class Date(var day: Int, var month: Int, var year: Int) {
    val monthText: String
        get() {
            return when (month) {
                1 -> "Январь"
                2 -> "Февраль"
                3 -> "Март"
                4 -> "Апрель"
                5 -> "Май"
                6 -> "Июнь"
                7 -> "Июль"
                8 -> "Август"
                9 -> "Сентябрь"
                10 -> "Октябрь"
                11 -> "Ноябрь"
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
            DayOfWeek.MONDAY.number -> DayOfWeek.MONDAY.text
            DayOfWeek.TUESDAY.number -> DayOfWeek.TUESDAY.text
            DayOfWeek.WEDNESDAY.number -> DayOfWeek.WEDNESDAY.text
            DayOfWeek.THURSDAY.number -> DayOfWeek.THURSDAY.text
            DayOfWeek.FRIDAY.number -> DayOfWeek.FRIDAY.text
            DayOfWeek.SATURDAY.number -> DayOfWeek.SATURDAY.text
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Date

        if (day != other.day) return false
        if (month != other.month) return false
        if (year != other.year) return false

        return true
    }

    override fun hashCode(): Int {
        var result = day
        result = 31 * result + month
        result = 31 * result + year
        return result
    }

    companion object {
        fun Calendar.toDate(): Date {
            return Date(this[Calendar.DAY_OF_MONTH],
                    this[Calendar.MONTH] + 1,
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

    /**
     * Номера дней недели взяты из класса @see java.time.DayOfWeek.
     *
     * @param number
     */
    enum class DayOfWeek(val number: Int, val text: String) {
        MONDAY(2, "ПН"),
        TUESDAY(3, "ВТ"),
        WEDNESDAY(4, "СР"),
        THURSDAY(5, "ЧТ"),
        FRIDAY(6, "ПТ"),
        SATURDAY(7, "СБ"),
        SUNDAY(1, "ВС")
    }
}