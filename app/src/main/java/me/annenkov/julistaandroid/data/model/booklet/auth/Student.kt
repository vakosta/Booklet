package me.annenkov.julistaandroid.data.model.booklet.auth

import com.google.gson.annotations.SerializedName

data class Student(
        @SerializedName("school_id")
        val schoolId: Int? = null,

        @SerializedName("name")
        val name: String? = null,

        @SerializedName("school_name")
        val schoolName: String? = null,

        @SerializedName("id")
        val id: Int? = null,

        @SerializedName("class")
        val jsonMemberClass: String? = null
)