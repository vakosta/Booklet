package com.booklet.bookletandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class DaysItem(
        @field:SerializedName("date")
        val date: String? = null,

        @field:SerializedName("subjects")
        val subjects: List<SubjectsItem?>? = null
)