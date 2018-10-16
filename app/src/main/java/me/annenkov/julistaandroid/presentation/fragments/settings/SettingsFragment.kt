package me.annenkov.julistaandroid.presentation.fragments.settings

import android.graphics.Color
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Preferences
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.yesButton

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.backgroundColor = Color.WHITE

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
                    Preferences.getInstance(activity!!).markPurpose = ""
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
}