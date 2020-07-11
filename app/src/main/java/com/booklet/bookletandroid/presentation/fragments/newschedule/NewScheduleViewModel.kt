package com.booklet.bookletandroid.presentation.fragments.newschedule

import androidx.lifecycle.ViewModel

class NewScheduleViewModel : ViewModel() {
    var currentWeekday: Weekday = Weekday.MONDAY
    var currentWeek: Long = 2

    fun setWeekday(weekday: Weekday) {

    }

    enum class Weekday(val number: Int) {
        MONDAY(1),
        TUESDAY(2),
        WEDNESDAY(3),
        THURSDAY(4),
        FRIDAY(5),
        SATURDAY(6),
        SUNDAY(7)
    }
}