package me.annenkov.julistaandroid.data.model.julista

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Progress {
    @SerializedName("subject_name")
    @Expose
    var subjectName: String? = null
    @SerializedName("avg_five")
    @Expose
    var avgFive: Double? = null
    @SerializedName("avg_hundred")
    @Expose
    var avgHundred: Double? = null
    @SerializedName("periods")
    @Expose
    var periods: List<Period>? = null
}