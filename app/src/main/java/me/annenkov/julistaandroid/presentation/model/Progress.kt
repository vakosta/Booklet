package me.annenkov.julistaandroid.presentation.model

data class Progress(
        val header: String,
        val subjectName: String,
        val avgFive: Double,
        val finalMark: Int?,
        val marks: List<Int>
)