package me.annenkov.julistaandroid.data.model.booklet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Auth {
    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("id")
    @Expose
    var id: Long? = null
    @SerializedName("secret")
    @Expose
    var secret: String? = null
    @SerializedName("created")
    @Expose
    var created: Boolean? = null
}