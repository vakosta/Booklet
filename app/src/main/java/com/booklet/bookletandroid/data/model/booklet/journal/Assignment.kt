package com.booklet.bookletandroid.data.model.booklet.journal

import com.google.gson.annotations.SerializedName

data class Assignment(
        @SerializedName("text")
        var text: String? = null
)