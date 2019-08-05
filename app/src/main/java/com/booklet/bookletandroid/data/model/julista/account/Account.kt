package com.booklet.bookletandroid.data.model.julista.account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Account {
    @SerializedName("student_name")
    @Expose
    val studentName: String? = null
    @SerializedName("student_grade")
    @Expose
    val studentGrade: String? = null
    @SerializedName("level")
    @Expose
    var level: Level? = null
    @SerializedName("scale_of_success")
    @Expose
    var scaleOfSuccess: Int? = null
    @SerializedName("top")
    @Expose
    var top: Top? = null
    @SerializedName("news")
    @Expose
    var news: List<News>? = null
}