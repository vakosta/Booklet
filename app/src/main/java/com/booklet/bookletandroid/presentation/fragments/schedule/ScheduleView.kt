package com.booklet.bookletandroid.presentation.fragments.schedule

import com.booklet.bookletandroid.presentation.FragmentBaseView
import hirondelle.date4j.DateTime

interface ScheduleView : FragmentBaseView {
    fun initPager()

    fun setViewPagerItem(item: Int, smooth: Boolean)

    fun paintWeekday(weekday: Int)
    fun paintWeek(week: ArrayList<DateTime>)
    fun paintCurrentDay()

    fun showCurrentDay()
    fun hideCurrentDay()
    fun showWeekdays()
    fun hideWeekdays()
}