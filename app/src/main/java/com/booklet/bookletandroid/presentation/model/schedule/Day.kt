package com.booklet.bookletandroid.presentation.model.schedule

import com.booklet.bookletandroid.data.model.booklet.journal.SubjectsItem
import com.booklet.bookletandroid.domain.model.Date
import com.booklet.bookletandroid.domain.model.Time

data class Day(
        val date: Date,

        val subjects: List<Subject>
) {
    companion object {
        fun List<SubjectsItem?>.toDay(): Day {
            return Day(Date(12, 12, 12), listOf())
        }

        fun getDefaultDay(date: Date) = Day(date, listOf(
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