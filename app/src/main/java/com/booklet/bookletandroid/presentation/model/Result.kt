package com.booklet.bookletandroid.presentation.model

data class Result(
        val subjectName: String,
        val marks: ArrayList<Int>,
        val finalMark: Int?
)