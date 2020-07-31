package com.booklet.bookletandroid.presentation.model.marks

data class Subject(
        val name: String,

        val periods: List<Period>,

        val yearMark: String
) {
    companion object {
        fun getDefaultSubject(name: String): Subject {
            return Subject(
                    name,
                    listOf(
                            Period(
                                    "1 семестр",
                                    listOf(
                                            Mark("", 1, "5")
                                    ),
                                    "5"
                            )
                    ),
                    "5"
            )
        }
    }
}