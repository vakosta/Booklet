package com.booklet.bookletandroid.presentation.fragments.schedulecard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.domain.model.Date
import com.booklet.bookletandroid.domain.repository.BaseRepository
import com.booklet.bookletandroid.domain.repository.ScheduleRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import com.booklet.bookletandroid.presentation.model.schedule.Day

class ScheduleCardViewModel(application: Application) : BaseViewModel(application) {
    override val repository: BaseRepository = ScheduleRepository(application.applicationContext)

    var cardLiveData = MutableLiveData<Day>()

    var isGotData = false
    var isShowing = false

    var mDate = Date(12, 12, 12)
    var isLoaded = false
}