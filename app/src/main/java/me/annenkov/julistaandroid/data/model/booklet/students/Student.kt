package me.annenkov.julistaandroid.data.model.booklet.students

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Student {
    @SerializedName("id")
    @Expose
    var id: Long? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
}