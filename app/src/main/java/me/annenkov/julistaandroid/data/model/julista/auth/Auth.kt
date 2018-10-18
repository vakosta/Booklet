package me.annenkov.julistaandroid.data.model.julista.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Auth {
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("pid")
    @Expose
    var pid: String? = null
    @SerializedName("student_profile_id")
    @Expose
    var studentProfileId: String? = null
    @SerializedName("bot_code")
    @Expose
    var botCode: String? = null
    @SerializedName("profiles")
    @Expose
    val profiles: List<Profile>? = null
}