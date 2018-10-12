package me.annenkov.julistaandroid.domain

import me.annenkov.julistaandroid.data.JulistaApi
import me.annenkov.julistaandroid.data.model.julista.*
import me.annenkov.julistaandroid.data.model.julista.account.Account
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    private const val BASE_URL = "https://api.julista.org/"

    private var retrofit: Retrofit? = null

    private fun getAPI(): JulistaApi {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        return retrofit!!.create<JulistaApi>(JulistaApi::class.java)
    }

    fun auth(login: String, password: String, fcmToken: String?): Call<Auth> {
        return ApiHelper.getAPI().auth(login, password, fcmToken)
    }

    fun getSchedule(token: String, pid: String, date: String): Call<List<Schedule>> {
        return ApiHelper.getAPI().getScheduleJulista(token, pid, date, date)
    }

    fun getProgress(token: String, pid: String): Call<List<Progress>> {
        return ApiHelper.getAPI().getProgressJulista(token, pid)
    }

    fun getAccount(token: String, pid: String): Call<Account> {
        return ApiHelper.getAPI().getAccount(token, pid)
    }

    fun checkNotificationsSubscription(pid: String): Call<ResultCheckNotificationsSubscription> {
        return ApiHelper.getAPI().checkNotificationsSubscription(pid)
    }

    fun setNotificationsSubscription(pid: String, token: String, hash: String)
            : Call<ResultSetNotificationsSubscription> {
        return ApiHelper.getAPI().setNotificationsSubscription(pid, token, hash)
    }
}