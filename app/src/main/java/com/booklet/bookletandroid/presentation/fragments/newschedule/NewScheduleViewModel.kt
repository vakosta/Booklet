package com.booklet.bookletandroid.presentation.fragments.newschedule

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.data.model.booklet.journal.DayItem
import com.booklet.bookletandroid.domain.repository.ScheduleRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import kotlinx.coroutines.launch

class NewScheduleViewModel(application: Application) : BaseViewModel(application) {
    override val repository = ScheduleRepository(application.applicationContext)

    val scheduleLiveData = MutableLiveData<List<DayItem?>>()

    var currentWeekday: Weekday = Weekday.MONDAY
    var currentWeek: Long = 2
    var withSaturdays = true

    fun getSchedule(start: String, end: String) {
        scope.launch {
            // TODO: Реализовать заполнение LiveData.
            val data = repository.getSchedule(start, end, true)

            scheduleLiveData.postValue(data.data!!.days)
        }
    }

    enum class Weekday(val number: Int) {
        MONDAY(1),
        TUESDAY(2),
        WEDNESDAY(3),
        THURSDAY(4),
        FRIDAY(5),
        SATURDAY(6),
        SUNDAY(7)
    }
}