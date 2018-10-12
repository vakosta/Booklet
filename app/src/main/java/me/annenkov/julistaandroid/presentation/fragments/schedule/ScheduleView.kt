package me.annenkov.julistaandroid.presentation.fragments.schedule

import hirondelle.date4j.DateTime
import me.annenkov.julistaandroid.presentation.FragmentBaseView

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