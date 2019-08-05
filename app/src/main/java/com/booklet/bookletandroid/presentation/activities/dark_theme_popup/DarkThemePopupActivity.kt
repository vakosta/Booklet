package com.booklet.bookletandroid.presentation.activities.dark_theme_popup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.domain.Preferences
import com.booklet.bookletandroid.presentation.activities.main.MainActivity
import kotlinx.android.synthetic.main.activity_dark_theme_popup.*

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