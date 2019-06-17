package me.annenkov.julistaandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class Response(
        @field:SerializedName("data")
        val data: Data? = null
)