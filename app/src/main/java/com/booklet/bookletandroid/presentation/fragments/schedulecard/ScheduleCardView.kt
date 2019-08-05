package com.booklet.bookletandroid.presentation.fragments.schedulecard

import com.booklet.bookletandroid.presentation.CardBaseView

interface ScheduleCardView : CardBaseView {
    fun stopRefreshing()
}