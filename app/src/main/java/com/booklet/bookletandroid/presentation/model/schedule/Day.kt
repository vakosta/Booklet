package com.booklet.bookletandroid.presentation.model.schedule

import com.booklet.bookletandroid.domain.model.Date

data class Day(
        val date: Date,

        val subjects: List<Subject>
) {
    companion object {
        fun getDefaultDay() {
            // TODO: Реализовать дефолтный день.
        }
    }
}