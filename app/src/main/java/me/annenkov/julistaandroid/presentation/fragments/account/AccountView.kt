package me.annenkov.julistaandroid.presentation.fragments.account

import android.widget.LinearLayout

interface AccountView {
    fun setBackgroundBlueColor()
    fun setBackgroundGreyColor()

    fun setStudentName(name: String)
    fun setStudentGrade(grade: String)
    fun setLevel(level: Int, intermediatePoints: Int)
    fun setXpLeft(xp: Int)
    fun setXpRatio(xpCount: Int, xpLeft: Int)

    fun setStatusText(text: String)
    fun setTop(layout: LinearLayout)
    fun setNews(layout: LinearLayout)

    fun showTop()
    fun showNews()

    fun removePadding()

    fun stopRefreshing()
}