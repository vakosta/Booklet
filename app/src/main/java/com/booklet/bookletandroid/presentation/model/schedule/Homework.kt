package com.booklet.bookletandroid.presentation.model.schedule

import java.io.Serializable

data class Homework(
        val text: String?,

        val urls: List<String>,

        val attachments: List<String>
) : Serializable