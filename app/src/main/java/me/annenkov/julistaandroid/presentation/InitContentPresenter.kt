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
                onFailureAttempt()
            } catch (e: Exception) {
                Crashlytics.logException(e)
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

    abstract fun onSuccessful(response: Any)
    abstract fun onFailureResponse()
    abstract fun onFailureNetwork()
    private fun onFailureAttempt() {
        doAsync {
            val response = ApiHelper.getInstance(mContext).auth(prefs.userLogin,
                    prefs.userPassword,
                    FirebaseInstanceId.getInstance().token, null)
            val newToken = response.token
            val botCode = response.botCode
            val students = response.students
            if (botCode != null)
                updateBotCodeIfNeed(botCode)
            if (students != null)
                prefs.userStudentProfiles = students
            if (newToken != null) {
                if (prefs.userStudentProfileId == "")
                    prefs.userStudentProfileId = response.studentProfileId ?: ""
                initContent()
            } else
                uiThread { onFailureResponse() }
        }
    }
}