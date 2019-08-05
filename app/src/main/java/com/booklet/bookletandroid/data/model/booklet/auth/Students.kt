package com.booklet.bookletandroid.data.model.booklet.auth

import com.google.gson.annotations.SerializedName

data class Students(
        @SerializedName("default")
        val jsonMemberDefault: Long? = null,

        @SerializedName("list")
        val list: List<Student>? = null
)