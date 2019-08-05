package com.booklet.bookletandroid.domain.repository

import android.content.Context

class MarksRepository(context: Context) : BaseRepository(context) {
    suspend fun getMarks(id: Long, secret: String) =
            client.getProgress(id, secret)
}