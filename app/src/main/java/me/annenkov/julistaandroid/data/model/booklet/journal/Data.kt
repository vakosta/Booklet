package me.annenkov.julistaandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class Data(
        @SerializedName("days")
        val days: List<DaysItem?>? = null
)