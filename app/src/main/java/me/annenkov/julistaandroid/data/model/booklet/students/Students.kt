package me.annenkov.julistaandroid.data.model.booklet.students

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Students {
    @SerializedName("students")
    @Expose
    var students: List<Student>? = null
}