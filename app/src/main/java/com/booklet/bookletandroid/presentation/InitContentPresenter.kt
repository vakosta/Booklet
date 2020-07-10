package com.booklet.bookletandroid.presentation

import android.content.Context
import com.booklet.bookletandroid.domain.Preferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.JsonParseException
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.ConnectException
import java.net.UnknownHostException

abstract class InitContentPresenter(private val mContext: Context) {
    val prefs = Preferences.getInstance(mContext)

    fun initContent() {
        doAsync {
            try {
                val response = executeMethod()
                uiThread { onSuccessful(response) }
            } catch (e: ConnectException) {
                uiThread { onFailureResponse() }
            } catch (e: UnknownHostException) {
                uiThread { onFailureNetwork() }
            } catch (e: JsonParseException) {
                onFailureAttempt()
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                if (prefs.userStudentProfileId == "")
                    onFailureAttempt()
                else
                    uiThread { onFailureResponse() }
            }
        }
    }

    abstract fun executeMethod(): Any

    private fun updateBotCodeIfNeed(code: String) {
        if (code != prefs.botCode)
            prefs.botCode = code
    }

    private fun updateInviteCodeIfNeed(code: String) {
        if (code != prefs.inviteCode)
            prefs.inviteCode = code
    }

    abstract fun onSuccessful(response: Any)
    abstract fun onFailureResponse()
    abstract fun onFailureNetwork()
    private fun onFailureAttempt() {
        /*doAsync {
            val response = ApiHelper.getInstance(mContext).auth(prefs.userLogin,
                    prefs.userPassword,
                    FirebaseInstanceId.getInstance().token, null)
            val newSecret = response.secret
            if (newSecret != null) {
                initContent()
            } else
                uiThread { onFailureResponse() }
        }*/
    }
}