package com.booklet.bookletandroid.domain.repository

import android.content.Context
import com.booklet.bookletandroid.data.model.booklet.journal.*

class ScheduleRepository(context: Context) : BaseRepository(context) {
    suspend fun getSchedule(start: String, end: String, isOffline: Boolean): Response {
        if (!isOffline) // TODO: Добавить получение id и secret.
            return client.getSchedule(1, "secret", start, end).body()!!

        return Response(Data(listOf(
                DayItem(start, listOf(
                        SubjectsItem(1,
                                "Алгебра",
                                Label("Модуль", "Заголовок"),
                                listOf("12", "59", "54"),
                                "Спортивный зал",
                                listOf(MarksItem("5", 1)),
                                listOf(Assignment("Выучить какой-то текст")))
                ))
        )))
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }
}