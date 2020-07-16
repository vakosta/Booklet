package com.booklet.bookletandroid.presentation.model.schedule

import com.booklet.bookletandroid.domain.model.Time

data class Subject(
        val number: Int,

        val name: String,

        val beginTime: Time,

        val endTime: Time,

        val marks: List<Mark>,

        val homeworks: List<Homework>,

        val lessonTopic: String?,

        val lessonModule: String?,

        val teacherComment: String?,

        val teacher: String?,

        val room: String?
)