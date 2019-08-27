package com.booklet.bookletandroid.presentation.activities.login

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.booklet.bookletandroid.data.model.booklet.auth.Auth
import com.booklet.bookletandroid.data.model.booklet.netschool_data.NetschoolData
import com.booklet.bookletandroid.domain.repository.AuthRepository
import com.booklet.bookletandroid.presentation.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(application: Application) : BaseViewModel(application) {
    override val repository = AuthRepository(application.applicationContext)
    val authLiveData = MutableLiveData<Response<Auth>?>()
    val netschoolRegionLiveData = MutableLiveData<Response<NetschoolData>?>()
    val netschoolProvinceLiveData = MutableLiveData<Response<NetschoolData>?>()
    val netschoolCityLiveData = MutableLiveData<Response<NetschoolData>?>()
    val netschoolSchoolLiveData = MutableLiveData<Response<NetschoolData>?>()

    val keyboardIsShowing = ObservableField(false)

    fun doAuth(diary: String,
               login: String,
               password: String,
               region: Int?,
               province: Int?,
               city: Int?,
               school: Int?) {
        scope.launch {
            val auth = repository.auth(diary, login, password, region, province, city, school)
            authLiveData.postValue(auth)
        }
    }

    fun getNetschoolData(region: Int?, province: Int?, city: Int?) {
        scope.launch {
            val data = repository.getNetschoolData(region, province, city)

            when {
                region == null ->
                    netschoolRegionLiveData.postValue(data)
                province == null ->
                    netschoolProvinceLiveData.postValue(data)
                city == null ->
                    netschoolCityLiveData.postValue(data)
                else ->
                    netschoolSchoolLiveData.postValue(data)
            }
        }
    }
}