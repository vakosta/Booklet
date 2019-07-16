package me.annenkov.julistaandroid.data.model.booklet.marks

import com.google.gson.annotations.SerializedName

data class MarksItem(
        @SerializedName("form")
        val form: String? = null,

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("weight")
        val weight: Int? = null,

        @SerializedName("value")
        val value: String? = null
)