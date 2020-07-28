package com.booklet.bookletandroid.presentation.model.schedule

import com.booklet.bookletandroid.data.model.booklet.journal.SubjectsItem
import com.booklet.bookletandroid.domain.model.Date
import com.booklet.bookletandroid.domain.model.Time

data class Day(
        val date: Date,

        val subjects: List<Subject>,

        val isForcibly: Boolean
) {
    companion object {
        fun List<SubjectsItem?>.toDay(): Day {
            return Day(Date(12, 12, 12), listOf(), false)
        }

        fun getDefaultDay(date: Date) = Day(date, listOf(
                Subject(1,
                        "${date}",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(Mark("5", 1, Mark.MarkType.GRADE)),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224"),

                Subject(2,
                        "Русский язык",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(Mark("4", 1, Mark.MarkType.GRADE)),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224"),

                Subject(3,
                        "Физическая культура",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(Mark("2", 1, Mark.MarkType.GRADE)),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224"),

                Subject(4,
                        "Информатика",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224"),

                Subject(5,
                        "Литература",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(Mark("3", 1, Mark.MarkType.GRADE)),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224"),

                Subject(6,
                        "География",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(Mark("3", 1, Mark.MarkType.GRADE)),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224"),

                Subject(7,
                        "Геометрия",
                        Time(15, 15),
                        Time(15, 40),
                        listOf(),
                        listOf(Homework("Выучить какой-то текст", listOf(), listOf())),
                        "Квадратные уравнения.",
                        "Общие понятия математики.",
                        "Молодец!",
                        "Кравченко Т.М.",
                        "№224")
        ), false)
    }
}