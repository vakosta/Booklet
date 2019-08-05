package com.booklet.bookletandroid.presentation.fragments.marks

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.data.model.booklet.marks.Data
import com.booklet.bookletandroid.domain.repository.MarksRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MarksViewModel(application: Application) : BaseViewModel(application) {
    override val repository = MarksRepository(application.applicationContext)
    val authLiveData = MutableLiveData<Response<Data>?>()

    val status = ObservableField(Status.LOADING)

    fun getMarks(id: Long, secret: String) {
        scope.launch {
            val marks = repository.getMarks(id, secret)
            authLiveData.postValue(marks)
        }
    }

    enum class Status {
        LOADED,
        LOADING,
        ERROR_NETWORK,
        ERROR_UNKNOWN,
    }
}