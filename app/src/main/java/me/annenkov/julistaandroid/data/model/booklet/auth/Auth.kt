package me.annenkov.julistaandroid.data.model.booklet.auth

import com.google.gson.annotations.SerializedName

data class Auth(
        @SerializedName("created")
        val created: Boolean? = null,

        @SerializedName("students")
        val students: Students? = null,

        @SerializedName("id")
        val id: Long? = null,

        @SerializedName("secret")
        val secret: String? = null,

        @SerializedName("message")
        val message: String? = null,

        @SerializedName("status")
        val status: Boolean? = null
)