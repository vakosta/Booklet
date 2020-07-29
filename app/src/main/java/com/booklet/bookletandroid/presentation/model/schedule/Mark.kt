package com.booklet.bookletandroid.presentation.model.schedule

import java.io.Serializable

data class Mark(
        val score: String?,

        val weight: Int?,

        val markType: MarkType
) : Serializable {
    override fun toString(): String {
        return score ?: "."
    }

    enum class MarkType {
        GRADE,
        POINT,
        N
    }
}