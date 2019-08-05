package com.booklet.bookletandroid.presentation

interface ActivityBaseView {
    fun onScroll(position: CardBaseView.ScrollPosition) {
        when (position) {
            CardBaseView.ScrollPosition.ABOVE_TOOLBAR -> hideToolbar()
            else -> showToolbar()
        }
    }

    fun showToolbar()
    fun hideToolbar()

    fun showMenu()
    fun hideMenu()
    fun setCalendarMenu()

    companion object {
        const val DURATION = 90L
    }
}