package com.booklet.bookletandroid.domain.repository

import android.content.Context

class EventsRepository(context: Context) : BaseRepository(context) {
    suspend fun getEvents(id: Long, secret: String) =
            client.getEvents(id, secret)
}