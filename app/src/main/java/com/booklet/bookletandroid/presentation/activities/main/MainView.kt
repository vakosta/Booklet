package com.booklet.bookletandroid.presentation.activities.main

import com.booklet.bookletandroid.presentation.ActivityBaseView

interface MainView : ActivityBaseView {
    fun initToolbar()
    fun initCalendar()
    fun initPager()

    fun setToolbarText(text: String)

    fun setFragment(position: Int)

    fun openDatePicker()

    fun setContentBelowToolbar(below: Boolean)
    fun setToolbarIsGone(gone: Boolean)
}