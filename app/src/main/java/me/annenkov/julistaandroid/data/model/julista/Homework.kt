package me.annenkov.julistaandroid.data.model.julista

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Homework {
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("attachments")
    @Expose
    var attachments: List<Any>? = null
}