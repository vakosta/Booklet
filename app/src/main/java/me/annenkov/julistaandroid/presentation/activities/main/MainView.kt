package me.annenkov.julistaandroid.presentation.activities.main

import me.annenkov.julistaandroid.presentation.ActivityBaseView

interface MainView : ActivityBaseView {
    fun initToolbar()
    fun initCalendar()
    fun initPager()

    fun setToolbarText(text: String)

    fun setFragment(position: Int)

    fun openDatePicker()

    fun setContentBelowToolbar(below: Boolean)
    fun setToolbarIsGone(gone: Boolean)

    fun setWhiteBackground()
    fun setBlueBackground()
}