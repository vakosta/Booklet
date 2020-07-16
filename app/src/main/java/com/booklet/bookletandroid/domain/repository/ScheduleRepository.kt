package com.booklet.bookletandroid.domain.repository

import android.content.Context
import com.booklet.bookletandroid.domain.model.Date
import com.booklet.bookletandroid.domain.model.Date.Companion.toDate
import com.booklet.bookletandroid.domain.model.Time
import com.booklet.bookletandroid.presentation.model.schedule.Day
import com.booklet.bookletandroid.presentation.model.schedule.Homework
import com.booklet.bookletandroid.presentation.model.schedule.Mark
import com.booklet.bookletandroid.presentation.model.schedule.Subject

class ScheduleRepository(context: Context) : BaseRepository(context) {
    suspend fun getSchedule(start: String, end: String, isDemo: Boolean): List<Day> {
        /*if (!isDemo) // TODO: Добавить получение id и secret.
            return client.getSchedule(1, "secret", start, end).body()!!*/

        return getDefaultSchedule(start.toDate(), end.toDate())
    }

    companion object {
        private val TAG = this::class.java.simpleName

        private fun getDefaultSchedule(start: Date, end: Date): ArrayList<Day> {
            val schedule = arrayListOf<Day>()

            for (i in 0..(end - start))
                schedule.add(getDefaultDay(start + i))

            return schedule
        }

        private fun getDefaultDay(date: Date) = Day(date, listOf(
                Subject(1,
                        "Алгебра",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(Mark("5", 1, Mark.MarkType.GRADE)),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224"),

                Subject(1,
                        "Алгебра",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(Mark("5", 1, Mark.MarkType.GRADE)),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224"),

                Subject(1,
                        "Алгебра",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(Mark("5", 1, Mark.MarkType.GRADE)),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224")
        ))
    }
}