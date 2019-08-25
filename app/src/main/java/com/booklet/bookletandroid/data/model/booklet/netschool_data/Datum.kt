package com.booklet.bookletandroid.data.model.booklet.netschool_data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Datum {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
}