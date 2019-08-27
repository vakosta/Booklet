package com.booklet.bookletandroid.presentation.fragments.events

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.data.model.booklet.events.Event
import com.booklet.bookletandroid.domain.repository.EventsRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class EventsViewModel(application: Application) : BaseViewModel(application) {
    override val repository = EventsRepository(application.applicationContext)
    val eventsLiveData = MutableLiveData<Response<ArrayList<Event>>?>()

    fun getEvents(id: Long, secret: String) {
        scope.launch {
            val auth = repository.getEvents(id, secret)
            eventsLiveData.postValue(auth)
        }
    }
}