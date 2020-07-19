package com.booklet.bookletandroid.domain.model

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class Date(var day: Int, var month: Int, var year: Int) {
    constructor() : this(0, 0, 0) {
        val date = Calendar.getInstance().toDate()

        day = date.day
        month = date.month
        year = date.year
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
        calendar.add(Calendar.DATE, 1)
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
}