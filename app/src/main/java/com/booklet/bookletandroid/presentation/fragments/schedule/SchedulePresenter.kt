package com.booklet.bookletandroid.presentation.fragments.schedule

import android.content.Context
import com.booklet.bookletandroid.domain.DateHelper
import com.booklet.bookletandroid.domain.Preferences
import hirondelle.date4j.DateTime

class SchedulePresenter(val view: ScheduleView, val mContext: Context) {
    var isCurrentDayShowing = true
    var isWeekdaysShowing = true

    var index = 0
    var date = DateHelper.getWorkdayDate(Preferences.getInstance(mContext).getWorkdayCount())

    var weekDay = 0
    var weekIndex = 0

    fun init() {
        view.initPager()
        view.paintCurrentDay()
    }

    fun setPosition(position: Int) {
        view.setViewPagerItem(position, Math.abs(index - position) <= 8)
        paintWeekByPosition(position)
    }

    fun setPosition(date: DateTime) {
        setPosition(index + DateHelper.numBusinessDaysFrom(this.date,
                date,
                Preferences.getInstance(mContext).saturdayLessons))
    }

    fun paintWeekByPosition(position: Int) {
        // TODO: Refactor this code
        val today = DateHelper
                .getWorkdayDate(Preferences.getInstance(mContext).getWorkdayCount())
        val date = today.plusDays(DateHelper.getAllDaysByBusinessDays(
                DateHelper.getWeekday(today),
                position - 5000,
                Preferences.getInstance(mContext).getWorkdayCount()
        ))

        if (DateHelper.getWeekIndex(date) != weekIndex) {
            view.paintWeek(DateHelper.getWeek(date))
            weekIndex = DateHelper.getWeekIndex(date)
        }

        view.paintWeekday(DateHelper.getWeekday(date))
        weekDay = DateHelper.getWeekday(date)

        index = position
        this.date = date
    }

    fun setWeekday(weekday: Int) {
        setPosition(index + (weekday - this.weekDay))
    }
}