package me.annenkov.julistaandroid.presentation.fragments.settings

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v14.preference.SwitchPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.model.PurchaseUpdate
import me.annenkov.julistaandroid.domain.model.Refresh
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.yesButton

class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.backgroundColor = Color.WHITE

        val main = (findPreference(this.getString(R.string.preference_notification_main))
                as SwitchPreference)
        val newMarks = (findPreference(this.getString(R.string.preference_notification_new_mark))
                as SwitchPreference)
        if (Preferences.getInstance(activity!!).notificationsSubscription) {
            main.isEnabled = true
            newMarks.isEnabled = true
        } else {
            main.isEnabled = false
            main.isChecked = false
            newMarks.isEnabled = false
            newMarks.isChecked = false
        }

        findPreference("button_vk").setOnPreferenceClickListener { _ ->
            browse(getString(R.string.url_vk_page))
            true
        }

        findPreference("button_exit").setOnPreferenceClickListener { _ ->
            alert("Вы уверены?") {
                yesButton {
                    Preferences.getInstance(activity!!).userLogin = ""
                    Preferences.getInstance(activity!!).userPassword = ""
                    Preferences.getInstance(activity!!).userToken = ""
                    Preferences.getInstance(activity!!).userPid = ""
                    Preferences.getInstance(activity!!).userStudentProfileId = ""
                    Preferences.getInstance(activity!!).botCode = ""
                    Preferences.getInstance(activity!!).markPurpose = 5
                    Preferences.getInstance(activity!!).saturdayLessons = false
                    Preferences.getInstance(activity!!).notificationMain = false
                    Preferences.getInstance(activity!!).notificationNewMark = false
                    Preferences.getInstance(activity!!).notificationNews = false
                    Preferences.getInstance(activity!!).notificationsSubscription = false
                    activity!!.recreate()
                }
                noButton {}
            }.show()
            true
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            getString(R.string.preference_mark_purpose) ->
                EventBus.getDefault().post(Refresh())
            getString(R.string.preference_saturday_lessons) ->
                EventBus.getDefault().post(Refresh())
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe
    fun onRefresh(purchaseUpdate: PurchaseUpdate) {
        preferenceScreen.removeAll()
        addPreferencesFromResource(R.xml.preferences)
    }
}