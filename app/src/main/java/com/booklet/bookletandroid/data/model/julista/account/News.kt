package com.booklet.bookletandroid.data.model.julista.account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class News {
    @SerializedName("content")
    @Expose
    var content: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
}