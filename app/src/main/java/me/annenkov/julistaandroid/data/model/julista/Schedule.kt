package me.annenkov.julistaandroid.data.model.julista

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Schedule {
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("date")
    @Expose
    var date: List<Int>? = null
    @SerializedName("begin_time")
    @Expose
    var beginTime: List<Int>? = null
    @SerializedName("end_time")
    @Expose
    var endTime: List<Int>? = null
    @SerializedName("day_number")
    @Expose
    var dayNumber: Int? = null
    @SerializedName("lesson_number")
    @Expose
    var lessonNumber: Int? = null
    @SerializedName("subject")
    @Expose
    var subject: String? = null
    @SerializedName("lesson_name")
    @Expose
    var lessonName: String? = null
    @SerializedName("comment")
    @Expose
    var comment: Any? = null
    @SerializedName("marks")
    @Expose
    var marks: List<Int>? = null
    @SerializedName("homework")
    @Expose
    var homework: Homework? = null
}