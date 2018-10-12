package me.annenkov.julistaandroid.presentation.fragments.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Preferences
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
                    activity!!.finish()
                }
                noButton {}
            }.show()
            true
        }
    }
}