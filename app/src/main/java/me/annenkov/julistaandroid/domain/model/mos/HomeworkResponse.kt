package me.annenkov.julistaandroid.domain.model.mos

class HomeworkResponse(
        val id: Int,
        val date: String,
        val subject: String,
        val description: String,
        val attachments: List<String>
)
