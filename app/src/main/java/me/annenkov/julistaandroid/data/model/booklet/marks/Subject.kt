package me.annenkov.julistaandroid.data.model.booklet.marks

import com.google.gson.annotations.SerializedName

data class Subject(
        @SerializedName("name")
        val name: String? = null,

        @SerializedName("periods")
        val periods: List<PeriodsItem?>? = null,

        @SerializedName("year_mark")
        val yearMark: String? = null
)