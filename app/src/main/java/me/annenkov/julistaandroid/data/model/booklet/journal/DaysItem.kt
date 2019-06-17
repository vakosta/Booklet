package me.annenkov.julistaandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class DaysItem(
        @field:SerializedName("date")
        val date: String? = null,

        @field:SerializedName("subjects")
        val subjects: List<SubjectsItem?>? = null
)