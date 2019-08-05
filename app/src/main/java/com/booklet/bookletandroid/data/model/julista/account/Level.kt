package com.booklet.bookletandroid.data.model.julista.account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Level {
    @SerializedName("level")
    @Expose
    var level: Int? = null
    @SerializedName("xp_count")
    @Expose
    var xpCount: Int? = null
    @SerializedName("xp_left")
    @Expose
    var xpLeft: Int? = null
    @SerializedName("percent")
    @Expose
    var percent: Int? = null
}