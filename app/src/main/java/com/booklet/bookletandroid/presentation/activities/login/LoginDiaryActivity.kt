package com.booklet.bookletandroid.presentation.activities.login

import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.booklet.bookletandroid.R
import kotlinx.android.synthetic.main.activity_login_diary.*

class LoginDiaryActivity : AppCompatActivity() {
    var selectedView: View? = null
    lateinit var selectedDiary: Diary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_diary)

        meshDiary.findViewById<TextView>(R.id.diaryName).text = "МЭШ (mos.ru)"
        mosregDiary.findViewById<TextView>(R.id.diaryName).text = "mosreg.ru"
        dnevnikruDiary.findViewById<TextView>(R.id.diaryName).text = "Дневник.ру"
        netschoolDiary.findViewById<TextView>(R.id.diaryName).text = "Сетевой город"

        meshDiary.setOnClickListener {
            startReverseTransition(selectedView)
            startTransition(it)
            selectedView = it
            selectedDiary = Diary.MOSRU
        }
        mosregDiary.setOnClickListener {
            startReverseTransition(selectedView)
            startTransition(it)
            selectedView = it
            selectedDiary = Diary.MOSREG
        }
        dnevnikruDiary.setOnClickListener {
            startReverseTransition(selectedView)
            startTransition(it)
            selectedView = it
            selectedDiary = Diary.DNEVNIKRU
        }
        netschoolDiary.setOnClickListener {
            startReverseTransition(selectedView)
            startTransition(it)
            selectedView = it
            selectedDiary = Diary.NETSCHOOL
        }

        loginEnterButton.setOnClickListener {
            val intent = if (selectedDiary != Diary.NETSCHOOL)
                Intent(this, LoginActivity::class.java)
            else
                Intent(this, NetSchoolLoginActivity::class.java)
            intent.putExtra(EXTRA_DIARY, selectedDiary)
            startActivity(intent)
        }
    }

    private fun startTransition(v: View?) {
        if (v != null) {
            val transition = v.background as TransitionDrawable
            transition.startTransition(230)
        }
    }

    private fun startReverseTransition(v: View?) {
        if (v != null) {
            val transition = v.background as TransitionDrawable
            transition.reverseTransition(230)
        }
    }

    companion object {
        const val EXTRA_DIARY = "diary"
    }

    enum class Diary(val apiName: String) {
        MOSRU("mosru"),
        MOSREG("mosreg"),
        DNEVNIKRU("dnevnikru"),
        NETSCHOOL("netschool"),
    }
}