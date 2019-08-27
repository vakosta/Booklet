package com.booklet.bookletandroid.data.model.booklet.events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Event {
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("value")
    @Expose
    var value: Int? = null
    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("personal_event")
    @Expose
    var personalEvent: Boolean? = null
}