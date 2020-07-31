package com.booklet.bookletandroid.presentation.fragments.marks

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.domain.model.Result
import com.booklet.bookletandroid.domain.repository.MarksRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import com.booklet.bookletandroid.presentation.model.marks.Subject
import kotlinx.coroutines.launch

class MarksViewModel(application: Application) : BaseViewModel(application) {
    override val repository = MarksRepository(application.applicationContext)
    val authLiveData = MutableLiveData<Result<List<Subject>>>()

    val status = ObservableField(Status.LOADING)

    fun getMarks(id: Long, secret: String) {
        scope.launch {
            val marks = repository.getMarks(id, secret, true)
            authLiveData.postValue(Result.success(data = marks))
        }
    }

    enum class Status {
        LOADED,
        LOADING,
        ERROR_NETWORK,
        ERROR_UNKNOWN,
    }
}