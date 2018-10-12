package me.annenkov.julistaandroid.data.model.julista.account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class TopList {
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("level")
    @Expose
    var level: Int? = null
}