package me.annenkov.julistaandroid.presentation.fragments.schedulecard

import me.annenkov.julistaandroid.presentation.CardBaseView

interface ScheduleCardView : CardBaseView {
    fun stopRefreshing()
}