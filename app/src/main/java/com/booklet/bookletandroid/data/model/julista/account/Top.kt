package com.booklet.bookletandroid.data.model.julista.account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Top {
    @SerializedName("top_list")
    @Expose
    var topList: List<TopList>? = null
    @SerializedName("my_position")
    @Expose
    var myPosition: Int? = null
}