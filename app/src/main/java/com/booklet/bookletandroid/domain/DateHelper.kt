package com.booklet.bookletandroid.domain

import com.booklet.bookletandroid.domain.model.Time
import hirondelle.date4j.DateTime
import java.util.*
import kotlin.math.abs

object DateHelper {
    fun getDate(): DateTime = DateTime.now(TimeZone.getDefault())

    fun getWorkdayDate(workdays: Int): DateTime {
        val date = getDate()
        val weekday = getWeekday(date)
        return if (weekday <= workdays) date
        else getFirstWeekday(date).plusDays(workdays - 1)
    }

    fun getFirstWeekday(date: DateTime): DateTime {
        var dateVar = date
        var firstWeekday = dateVar
        val monday = 2
        if (dateVar.weekDay == 1)
            dateVar = dateVar.minusDays(1)
        val weekday = dateVar.weekDay
        if (weekday > monday) {
            val numDaysFromSunday = weekday - monday
            firstWeekday = dateVar.minusDays(numDaysFromSunday)
        }
        return firstWeekday
    }

    fun getWeek(date: DateTime): ArrayList<DateTime> {
        val firstWeekday = getFirstWeekday(date)
        return arrayListOf(
                firstWeekday,
                firstWeekday.plusDays(1),
                firstWeekday.plusDays(2),
                firstWeekday.plusDays(3),
                firstWeekday.plusDays(4),
                firstWeekday.plusDays(5),
                firstWeekday.plusDays(6)
        )
    }

    fun getWeekIndex(date: DateTime): Int {
        val index = date.weekIndex
        return when {
            date.weekDay == 1 -> index - 1
            else -> index
        }
    }

    fun getWeekday(date: DateTime): Int {
        val weekday = date.weekDay - 1
        return when (weekday) {
            0 -> 7
            else -> weekday
        }
    }

    fun getAllDaysByBusinessDays(dayOfWeek: Int, businessDays: Int, workdays: Int): Int {
        var result = 0
        if (businessDays != 0) {
            val isStartOnWorkday = dayOfWeek < 6
            val absBusinessDays = Math.abs(businessDays)

            result = if (isStartOnWorkday) {
                val shiftedWorkday = if (businessDays > 0) dayOfWeek else 6 - dayOfWeek
                absBusinessDays + (absBusinessDays + shiftedWorkday -
                        if (absBusinessDays != businessDays && workdays == 6) 0 else 1) /
                        workdays * (7 - workdays)
            } else {
                val shiftedWeekend = if (businessDays > 0) dayOfWeek else 13 - dayOfWeek
                absBusinessDays + (absBusinessDays -
                        if (absBusinessDays != businessDays && workdays == 6) 0 else 1) /
                        workdays * (7 - workdays) + (7 - shiftedWeekend)
            }

            if (absBusinessDays != businessDays)
                result = -result
        }
        return result
    }

    fun numBusinessDaysFrom(firstDate: DateTime,
                            secondDate: DateTime,
                            sixBusinessDays: Boolean): Int {
        val days = firstDate.numDaysFrom(secondDate)
        val startW = if (days < 0) -getWeekday(secondDate) else getWeekday(firstDate)

        return days - (if (sixBusinessDays) 1 else 2) * ((days + startW) / 7)
    }

    fun isTimeInInterval(time: Time, beginTime: Time, endTime: Time): Boolean {
        val resultTime = time.hour * 60 + time.minute
        return resultTime >= beginTime.hour * 60 + beginTime.minute &&
                resultTime < endTime.hour * 60 + endTime.minute
    }

    fun numTimeFrom(firstTime: Time, secondTime: Time): Int {
        return abs((secondTime.hour * 60 + secondTime.minute)
                - (firstTime.hour * 60 + firstTime.minute))
    }

    fun getCurrentTime(): Time {
        val date = getDate()
        return Time(date.hour, date.minute)
    }

    fun getMonthNameByNumber(number: Int): String {
        return when (number) {
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

    fun getEndTime(beginTime: List<Int>): MutableList<Int> {
        val durability = 45
        val endTime = mutableListOf(beginTime[0], beginTime[1])
        endTime[0] += (beginTime[1] + durability - 1) / 59
        endTime[1] = (beginTime[1] + durability) % (59 + 1)
        return endTime
    }

    fun getTermString(date: String): String {
        val dateArr = date.split("-")
        val dateTime = DateTime(dateArr[0].toInt(),
                dateArr[1].toInt(),
                dateArr[2].toInt(), 1, 1, 1, 1)

        val diff = dateTime.numDaysFrom(getDate())
        return when (diff) {
            0 -> "Сегодня"
            1 -> "Вчера"
            2 -> "Позавчера"
            else -> "$diff дня(ей) назад"
        }
    }
}