package me.annenkov.julistaandroid.presentation.activities.login

import android.content.Context
import com.google.firebase.iid.FirebaseInstanceId
import me.annenkov.julistaandroid.data.model.julista.Auth
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.domain.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val view: LoginView) {
    fun login(context: Context, login: String, password: String) {
        view.startLoading()
        ApiHelper.auth(login, password, FirebaseInstanceId.getInstance().token)
                .enqueue(object : Callback<Auth> {
                    override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                        val auth = response.body()
                        if (auth?.token == null || auth.pid == null || auth.botCode == null) {
                            view.onLoginFailed()
                            return
                        }

                        val prefs = Preferences.getInstance(context)
                        prefs.userLogin = login
                        prefs.userPassword = password
                        prefs.userToken = auth.token!!
                        prefs.userPid = auth.pid!!
                        prefs.userStudentProfileId = auth.studentProfileId!!
                        prefs.botCode = auth.botCode!!

                        view.onLoginSuccessful()
                    }

                    override fun onFailure(call: Call<Auth>?, t: Throwable?) {
                        view.onNetworkProblems()
                    }
                })
    }
}