package com.booklet.bookletandroid.data.model.booklet.marks

import com.google.gson.annotations.SerializedName

data class PeriodItem(
        @SerializedName("marks")
        val marks: List<MarkItem?>? = null,

        @SerializedName("title")
        val title: String? = null,

        @SerializedName("final_mark")
        val finalMark: String? = null
)