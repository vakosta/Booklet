package me.annenkov.julistaandroid.data.model.booklet.marks

import com.google.gson.annotations.SerializedName

data class Data(
        @SerializedName("data")
        val data: List<Subject>? = null
)