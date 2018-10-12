package me.annenkov.julistaandroid.data.model.julista

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultCheckNotificationsSubscription {
    @SerializedName("result")
    @Expose
    var result: Boolean? = null
}