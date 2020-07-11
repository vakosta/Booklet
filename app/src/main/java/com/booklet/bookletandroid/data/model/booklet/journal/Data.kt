package com.booklet.bookletandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class Data(
        @SerializedName("days")
        val days: List<DayItem?>? = null
)