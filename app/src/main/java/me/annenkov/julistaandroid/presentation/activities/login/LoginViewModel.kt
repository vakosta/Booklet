package me.annenkov.julistaandroid.presentation.activities.login

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import kotlinx.coroutines.launch
import me.annenkov.julistaandroid.data.model.booklet.auth.Auth
import me.annenkov.julistaandroid.domain.repository.AuthRepository
import me.annenkov.julistaandroid.presentation.BaseViewModel
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