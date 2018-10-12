package me.annenkov.julistaandroid.data.model.julista

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Pid {
    @SerializedName("pid")
    @Expose
    var pid: String? = null
}