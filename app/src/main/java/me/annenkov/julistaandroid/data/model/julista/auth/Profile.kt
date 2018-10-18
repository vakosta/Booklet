package me.annenkov.julistaandroid.data.model.julista.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Profile {
    @SerializedName("student_profile_id")
    @Expose
    var studentProfileId: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
}