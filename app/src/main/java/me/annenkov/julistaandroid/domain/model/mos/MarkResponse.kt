package me.annenkov.julistaandroid.domain.model.mos

data class MarkResponse(val scheduleLessonId: Int,
                        val date: String,
                        val subject: String,
                        val mark: Int,
                        val isExam: Boolean,
                        val isPoint: Boolean)