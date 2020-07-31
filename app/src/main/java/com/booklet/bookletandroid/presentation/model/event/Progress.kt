package com.booklet.bookletandroid.presentation.model.event

import com.booklet.bookletandroid.presentation.model.marks.Mark

data class Progress(
        val header: String,
        val subjectName: String,
        val avgFive: Double,
        val finalMark: Int?,
        val marks: List<Mark>
)