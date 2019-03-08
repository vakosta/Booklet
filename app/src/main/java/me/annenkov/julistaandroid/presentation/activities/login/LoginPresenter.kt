package me.annenkov.julistaandroid.presentation.activities.login

import android.content.Context
import com.google.firebase.iid.FirebaseInstanceId
import me.annenkov.julistaandroid.data.model.julista.auth.Auth
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.presentation.InitContentPresenter

class LoginPresenter(
        private val view: LoginView,
        private val mContext: Context
) : InitContentPresenter(mContext) {
    private var mLogin = ""
    private var mPassword = ""
    private var mInviteCode = ""

    fun login(login: String, password: String, inviteCode: String) {
        view.startLoading()
        mLogin = login
        mPassword = password
        mInviteCode = inviteCode
        initContent()
    }

    override fun executeMethod(): Any = ApiHelper.getInstance(mContext).auth(mLogin, mPassword,
            FirebaseInstanceId.getInstance().token, mInviteCode)

    override fun onSuccessful(response: Any) {
        val auth = (response as Auth)
        try {
            view.onLoginSuccessful(mLogin,
                    mPassword,
                    response.token!!,
                    auth.pid!!,
                    auth.studentProfileId!!,
                    auth.botCode!!,
                    auth.inviteCode!!,
                    auth.invitations!!,
                    auth.students!!)
        } catch (e: KotlinNullPointerException) {
            onFailureResponse()
        }
    }

    override fun onFailureResponse() {
        view.onLoginFailed()
    }

    override fun onFailureNetwork() {
        view.onNetworkProblems()
    }
}