package com.booklet.bookletandroid.domain.repository

import android.content.Context
import com.booklet.bookletandroid.domain.model.Date
import com.booklet.bookletandroid.presentation.model.schedule.Day

class ScheduleRepository(context: Context) : BaseRepository(context) {
    suspend fun getSchedule(start: Date, end: Date, isDemo: Boolean): List<Day> {
        /*if (!isDemo) // TODO: Добавить получение id и secret.
            return client.getSchedule(1, "secret", start, end).body()!!*/

        return getDefaultSchedule(start, end)
    }

    companion object {
        private val TAG = this::class.java.simpleName

        private fun getDefaultSchedule(start: Date, end: Date): ArrayList<Day> {
            val schedule = arrayListOf<Day>()

            for (i in 0..(end - start))
                schedule.add(Day.getDefaultDay(start + i))

            return schedule
        }
    }
}