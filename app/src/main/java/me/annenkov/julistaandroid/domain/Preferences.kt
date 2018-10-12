package me.annenkov.julistaandroid.domain

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.preference.PreferenceManager
import me.annenkov.julistaandroid.R

class Preferences private constructor(context: Context) {
    private val KEY_BOT_CODE = context.getString(R.string.preference_bot_code)
    private val KEY_LOGIN = context.getString(R.string.preference_login)
    private val KEY_PASSWORD = context.getString(R.string.preference_password)
    private val KEY_TOKEN = context.getString(R.string.preference_token)
    private val KEY_PID = context.getString(R.string.preference_pid)
    private val KEY_STUDENT_PROFILE_ID = context.getString(R.string.preference_student_profile_id)
    private val KEY_SATURDAY_LESSONS = context.getString(R.string.preference_saturday_lessons)
    private val KEY_MARK_PURPOSE = context.getString(R.string.preference_mark_purpose)
    private val KEY_NOTIFICATION_MAIN = context.getString(R.string.preference_notification_main)
    private val KEY_NOTIFICATION_NEW_MARK = context.getString(R.string.preference_notification_new_mark)
    private val KEY_NOTIFICATION_NEWS = context.getString(R.string.preference_notification_news)
    private val KEY_BOUGHT_NOTIFICATIONS = context.getString(R.string.preference_bought_notifications)

    private val prefs: SharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)

    var botCode: String
        get() = prefs.getString(KEY_BOT_CODE, "")
        set(value) = prefs.edit().putString(KEY_BOT_CODE, value).apply()

    var userLogin: String
        get() = prefs.getString(KEY_LOGIN, "")
        set(value) = prefs.edit().putString(KEY_LOGIN, value).apply()

    var userPassword: String
        get() = prefs.getString(KEY_PASSWORD, "")
        set(value) = prefs.edit().putString(KEY_PASSWORD, value).apply()

    var userToken: String
        get() = prefs.getString(KEY_TOKEN, "")
        set(value) = prefs.edit().putString(KEY_TOKEN, value).apply()

    var userPid: String
        get() = prefs.getString(KEY_PID, "")
        set(value) = prefs.edit().putString(KEY_PID, value).apply()

    var userStudentProfileId: String
        get() = prefs.getString(KEY_STUDENT_PROFILE_ID, "")
        set(value) = prefs.edit().putString(KEY_STUDENT_PROFILE_ID, value).apply()

    var saturdayLessons: Boolean
        get() = prefs.getBoolean(KEY_SATURDAY_LESSONS, false)
        set(value) = prefs.edit().putBoolean(KEY_SATURDAY_LESSONS, value).apply()

    var markPurpose: String
        get() = prefs.getString(KEY_MARK_PURPOSE, "5")
        set(value) = prefs.edit().putString(KEY_MARK_PURPOSE, value).apply()

    var notificationMain: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_MAIN, false)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATION_MAIN, value).apply()

    var notificationNewMark: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_NEW_MARK, false)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATION_NEW_MARK, value).apply()

    var notificationNews: Boolean
        get() = prefs.getBoolean(KEY_NOTIFICATION_NEWS, false)
        set(value) = prefs.edit().putBoolean(KEY_NOTIFICATION_NEWS, value).apply()

    var notificationsSubscription: Boolean
        get() = prefs.getBoolean(KEY_BOUGHT_NOTIFICATIONS, false)
        set(value) = prefs.edit().putBoolean(KEY_BOUGHT_NOTIFICATIONS, value).apply()

    fun getWorkdayCount(): Int {
        return if (saturdayLessons)
            6
        else
            5
    }

    companion object : SingletonHolder<Preferences, Context>(::Preferences)
}