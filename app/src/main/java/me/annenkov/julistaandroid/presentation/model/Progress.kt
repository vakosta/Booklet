package me.annenkov.julistaandroid.presentation.model

import me.annenkov.julistaandroid.data.model.booklet.marks.MarksItem

data class Progress(
        val header: String,
        val subjectName: String,
        val avgFive: Double,
        val finalMark: Int?,
        val marks: List<MarksItem?>
)