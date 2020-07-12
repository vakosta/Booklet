package com.booklet.bookletandroid.presentation.fragments.newschedulecard

import android.app.Application
import com.booklet.bookletandroid.domain.repository.BaseRepository
import com.booklet.bookletandroid.domain.repository.ScheduleRepository
import com.booklet.bookletandroid.presentation.BaseViewModel

class NewScheduleCardViewModel(application: Application) : BaseViewModel(application) {
    override val repository: BaseRepository = ScheduleRepository(application.applicationContext)
}