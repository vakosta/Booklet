package com.booklet.bookletandroid.presentation.model.schedule

data class Mark(
        val score: String?,

        val weight: Int?,

        val markType: MarkType
) {
    enum class MarkType {
        GRADE,
        POINT,
        N
    }
}