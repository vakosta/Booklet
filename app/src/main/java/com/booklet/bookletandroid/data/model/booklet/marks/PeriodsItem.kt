package com.booklet.bookletandroid.data.model.booklet.marks

import com.google.gson.annotations.SerializedName

data class PeriodsItem(
        @SerializedName("marks")
        val marks: List<MarksItem?>? = null,

        @SerializedName("title")
        val title: String? = null,

        @SerializedName("final_mark")
        val finalMark: String? = null
)