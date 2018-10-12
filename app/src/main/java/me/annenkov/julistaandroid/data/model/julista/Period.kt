package me.annenkov.julistaandroid.data.model.julista

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import me.annenkov.julistaandroid.data.model.mos.progress.Mark

class Period {
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("avg_five")
    @Expose
    var avgFive: Double? = null
    @SerializedName("avg_hundred")
    @Expose
    var avgHundred: Double? = null
    @SerializedName("final_mark")
    @Expose
    var finalMark: Int? = null
    @SerializedName("start")
    @Expose
    var start: String? = null
    @SerializedName("end")
    @Expose
    var end: String? = null
    @SerializedName("marks")
    @Expose
    var marks: List<Mark>? = null
}