package me.annenkov.julistaandroid.presentation.activities.login

import android.content.Context
import com.google.firebase.iid.FirebaseInstanceId
import me.annenkov.julistaandroid.data.model.julista.Auth
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.presentation.InitContentPresenter

class LoginPresenter(
        private val view: LoginView,
        private val mContext: Context
) : InitContentPresenter(mContext) {
    private var mLogin = ""
    private var mPassword = ""

    fun login(login: String, password: String) {
        view.startLoading()
        mLogin = login
        mPassword = password
        initContent()
    }

    override fun executeMethod(): Any = ApiHelper.auth(mLogin, mPassword,
            FirebaseInstanceId.getInstance().token).execute().body()!!

    override fun onSuccessful(response: Any) {
        val auth = (response as Auth)
        val prefs = Preferences.getInstance(mContext)
        prefs.userLogin = mLogin
        prefs.userPassword = mPassword
        prefs.userToken = response.token!!
        prefs.userPid = auth.pid!!
        prefs.userStudentProfileId = auth.studentProfileId!!
        prefs.botCode = auth.botCode!!
        view.onLoginSuccessful()
    }

    override fun onFailureResponse() {
        view.onLoginFailed()
    }

    override fun onFailureNetwork() {
        view.onNetworkProblems()
    }
}