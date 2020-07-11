package com.booklet.bookletandroid.presentation.activities.login

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.data.model.booklet.auth.Auth
import com.booklet.bookletandroid.data.model.booklet.netschool_data.NetschoolData
import com.booklet.bookletandroid.domain.model.Result
import com.booklet.bookletandroid.domain.repository.AuthRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {
    override val repository = AuthRepository(application.applicationContext)

    val authLiveData = MutableLiveData<Result<Auth>?>()
    val netschoolRegionLiveData = MutableLiveData<Result<NetschoolData>?>()
    val netschoolProvinceLiveData = MutableLiveData<Result<NetschoolData>?>()
    val netschoolCityLiveData = MutableLiveData<Result<NetschoolData>?>()
    val netschoolSchoolLiveData = MutableLiveData<Result<NetschoolData>?>()

    val keyboardIsShowing = ObservableField(false)

    /**
     * Метод для отправки запроса на авторизацию.
     *
     * @param diary это имя дневника для авторизации.
     * @param login это логин пользователя.
     * @param password это пароль пользователя.
     */
    fun doAuth(diary: String,
               login: String,
               password: String) {
        scope.launch {
            authLiveData.postValue(Result.loading(data = null))

            val auth = repository.auth(diary, login, password)
            if (auth.body() != null && auth.isSuccessful)
                authLiveData.postValue(Result.success(data = auth.body()!!))
            else
                authLiveData.postValue(Result.error(data = null,
                        errorType = Result.ErrorType.MISSING_NETWORK_ERROR,
                        message = "Возникла ошибка!"))
        }
    }

    /**
     * Метод для отправки запроса на авторизацию специально
     * для дневника NetSchool.
     *
     * @param diary это имя дневника для авторизации.
     * @param login это логин пользователя.
     * @param password это пароль пользователя.
     * @param region это регион школы.
     * @param province это провинция школы.
     * @param city это город школы.
     * @param school это номер школы.
     */
    fun doAuth(diary: String,
               login: String,
               password: String,
               region: Int?,
               province: Int?,
               city: Int?,
               school: Int?) {
        scope.launch {
            authLiveData.postValue(Result.loading(data = null))

            val auth = repository.auth(diary, login, password, region, province, city, school)
            if (auth.body() != null && auth.isSuccessful)
                authLiveData.postValue(Result.success(data = auth.body()!!))
            else
                authLiveData.postValue(Result.error(data = null,
                        errorType = Result.ErrorType.MISSING_NETWORK_ERROR,
                        message = "Возникла ошибка!"))
        }
    }

    /**
     * Метод для получения списка регионов/провинций/городов/школ для
     * авторизации в дневнике NetSchool.
     *
     * Загружает данные, основываясь на входных параметрах.
     *
     * @param region это регион школы.
     * @param province это провинция школы.
     * @param city это город школы.
     */
    fun getNetschoolData(region: Int?, province: Int?, city: Int?) {
        scope.launch {
            val data = repository.getNetschoolData(region, province, city)

            when {
                region == null ->
                    netschoolRegionLiveData.postValue(Result.success(data = data.body()!!))
                province == null ->
                    netschoolProvinceLiveData.postValue(Result.success(data = data.body()!!))
                city == null ->
                    netschoolCityLiveData.postValue(Result.success(data = data.body()!!))
                else ->
                    netschoolSchoolLiveData.postValue(Result.success(data = data.body()!!))
            }
        }
    }
}