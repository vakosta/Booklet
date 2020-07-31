package com.booklet.bookletandroid.domain.repository

import android.content.Context
import com.booklet.bookletandroid.presentation.model.marks.Subject

class MarksRepository(context: Context) : BaseRepository(context) {
    suspend fun getMarks(id: Long, secret: String, isDemo: Boolean): List<Subject> {
        // client.getProgress(id, secret)

        return getDefaultMarks()
    }

    companion object {
        private fun getDefaultMarks(): ArrayList<Subject> {
            val marks = arrayListOf<Subject>()

            for (i in 0..1)
                marks.add(Subject.getDefaultSubject("122"))

            return marks
        }
    }
}