package com.booklet.bookletandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class Label(
        @field:SerializedName("module")
        val module: String? = null,

        @field:SerializedName("title")
        val title: String? = null
)