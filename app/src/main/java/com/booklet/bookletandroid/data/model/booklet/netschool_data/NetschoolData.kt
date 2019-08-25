package com.booklet.bookletandroid.data.model.booklet.netschool_data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class NetschoolData {
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null
}