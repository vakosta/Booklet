package com.booklet.bookletandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class SubjectsItem(
        @field:SerializedName("number")
        val number: Int? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("label")
        val label: Label? = null,

        @field:SerializedName("time")
        val time: List<String?>? = null,

        @field:SerializedName("room")
        val room: String? = null,

        @field:SerializedName("marks")
        val marks: List<MarksItem?>? = null,

        @field:SerializedName("assignments")
        val assignments: List<Assignment?>? = null
)