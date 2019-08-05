package com.booklet.bookletandroid.presentation

import android.view.ViewGroup

interface CardBaseView {
    enum class ScrollPosition {
        TOP,
        ABOVE_PERIODS,
        ABOVE_TOOLBAR
    }

    fun setContentLayout(layout: ViewGroup)
    fun setEmptyContentLayout()

    fun setHeaderText(text: String)
}