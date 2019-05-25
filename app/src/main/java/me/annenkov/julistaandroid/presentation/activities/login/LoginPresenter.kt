package me.annenkov.julistaandroid.presentation.activities.login

import android.content.Context
import com.google.firebase.iid.FirebaseInstanceId
import me.annenkov.julistaandroid.data.model.booklet.Auth
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.presentation.InitContentPresenter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
            doAsync {
                val students = ApiHelper
                        .getInstance(mContext)
                        .getStudents(prefs.userPid!!, prefs.userSecret!!)
                uiThread { }
            }
            view.onLoginSuccessful(mLogin,
                    mPassword,
                    response.secret!!,
                    arrayListOf())
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