package com.booklet.bookletandroid.presentation.activities.login

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.data.model.booklet.auth.Auth
import com.booklet.bookletandroid.domain.repository.AuthRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(application: Application) : BaseViewModel(application) {
    override val repository = AuthRepository(application.applicationContext)
    val authLiveData = MutableLiveData<Response<Auth>?>()

    val keyboardIsShowing = ObservableField(false)

    fun doAuth(diary: String, login: String, password: String) {
        scope.launch {
            val auth = repository.auth(diary, login, password)
            authLiveData.postValue(auth)
        }
    }
}