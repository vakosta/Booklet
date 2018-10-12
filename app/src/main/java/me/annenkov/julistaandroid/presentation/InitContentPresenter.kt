package me.annenkov.julistaandroid.presentation

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonParseException
import me.annenkov.julistaandroid.domain.ApiHelper
import me.annenkov.julistaandroid.domain.Preferences
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
                uiThread { onFailureAttempt() }
            } catch (e: Exception) {
                Crashlytics.logException(e)
                if (prefs.userStudentProfileId == "")
                    uiThread { onFailureAttempt() }
                else
                    uiThread { onFailureResponse() }
            }
        }
    }

    abstract fun executeMethod(): Any

    private fun updateToken(token: String) {
        prefs.userToken = token
    }

    private fun updateBotCodeIfNeed(code: String) {
        if (code != prefs.botCode)
            prefs.botCode = code
    }

    abstract fun onSuccessful(response: Any)
    abstract fun onFailureResponse()
    abstract fun onFailureNetwork()
    private fun onFailureAttempt() {
        doAsync {
            val response = ApiHelper.auth(prefs.userLogin,
                    prefs.userPassword,
                    FirebaseInstanceId.getInstance().token).execute().body()
            val newToken = response?.token ?: ""
            val botCode = response?.botCode ?: ""
            uiThread {
                if (botCode.isNotEmpty())
                    updateBotCodeIfNeed(botCode)
                if (newToken.isNotEmpty()) {
                    updateToken(newToken)
                    if (prefs.userStudentProfileId == "")
                        prefs.userStudentProfileId = response?.studentProfileId ?: ""
                    initContent()
                } else
                    onFailureResponse()
            }
        }
    }
}