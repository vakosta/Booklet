package me.annenkov.julistaandroid.domain

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import me.annenkov.julistaandroid.data.model.booklet.auth.Auth
import kotlin.coroutines.CoroutineContext

class TestViewModel(application: Application) : AndroidViewModel(application) {
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    private val api = ApiHelper
            .getInstance(application.applicationContext)
            .getAPI(ApiHelper.ApiType.BOOKLET)
    val popularMoviesLiveData = MutableLiveData<Auth>()

    fun doAuth(diary: String, login: String, password: String) {
        scope.launch {
            val popularMovies = api.auth(diary, login, password)
            popularMoviesLiveData.postValue(popularMovies.body())
        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()
}