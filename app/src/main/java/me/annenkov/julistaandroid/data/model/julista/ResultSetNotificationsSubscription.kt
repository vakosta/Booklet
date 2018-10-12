package me.annenkov.julistaandroid.data.model.julista

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultSetNotificationsSubscription {
    @SerializedName("result")
    @Expose
    var result: String? = null
}