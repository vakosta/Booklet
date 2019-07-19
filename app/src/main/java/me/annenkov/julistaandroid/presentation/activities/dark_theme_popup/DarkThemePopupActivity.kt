package me.annenkov.julistaandroid.presentation.activities.dark_theme_popup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dark_theme_popup.*
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.presentation.activities.main.MainActivity

class DarkThemePopupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_theme_popup)
        Preferences.getInstance(this).isShowedDarkThemePopup = true

        turnOnDarkTheme.setOnClickListener {
            Preferences.getInstance(this).isDarkTheme = true
            setResult(MainActivity.RESULT_SET_DARK_THEME)
            finish()
        }
        cancel.setOnClickListener {
            setResult(MainActivity.RESULT_CANCEL)
            finish()
        }
    }
}