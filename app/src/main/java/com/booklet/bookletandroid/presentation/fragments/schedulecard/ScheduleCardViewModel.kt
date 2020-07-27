package com.booklet.bookletandroid.presentation.fragments.schedulecard

import android.app.Application
import com.booklet.bookletandroid.domain.model.Date
import com.booklet.bookletandroid.domain.repository.BaseRepository
import com.booklet.bookletandroid.domain.repository.ScheduleRepository
import com.booklet.bookletandroid.presentation.BaseViewModel

class ScheduleCardViewModel(application: Application) : BaseViewModel(application) {
    override val repository: BaseRepository = ScheduleRepository(application.applicationContext)

    var mDate = Date(12, 12, 12)
    var isLoaded = false
}