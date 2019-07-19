package me.annenkov.julistaandroid.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.domain.repository.BaseRepository
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default
    val scope = CoroutineScope(coroutineContext)

    private val api = ApiHelper
            .getInstance(application.applicationContext)
            .getAPI()

    abstract val repository: BaseRepository

    fun cancelAllRequests() = coroutineContext.cancel()
}