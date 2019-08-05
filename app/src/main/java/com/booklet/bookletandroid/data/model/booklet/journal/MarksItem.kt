package com.booklet.bookletandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class MarksItem(
        @field:SerializedName("score")
        val score: String? = null,

        @field:SerializedName("weight")
        val weight: Int? = null
)