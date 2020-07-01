package com.booklet.bookletandroid.presentation.activities.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.booklet.bookletandroid.R
import com.booklet.bookletandroid.data.model.booklet.auth.Student
import com.booklet.bookletandroid.databinding.ActivityLoginBinding
import com.booklet.bookletandroid.domain.Preferences
import com.booklet.bookletandroid.domain.px
import com.booklet.bookletandroid.presentation.activities.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.jetbrains.anko.alert
import org.jetbrains.anko.browse
import org.jetbrains.anko.selector
import org.jetbrains.anko.yesButton

open class LoginActivity : AppCompatActivity() {
    protected lateinit var binding: ActivityLoginBinding
    protected lateinit var diaryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.executePendingBindings()

        val diary = intent.getSerializableExtra(LoginDiaryActivity.EXTRA_DIARY)
                as LoginDiaryActivity.Diary
        diaryName = diary.apiName

        val title = when (diary) {
            LoginDiaryActivity.Diary.MOSRU -> "mos.ru"
            LoginDiaryActivity.Diary.MOSREG -> "mosreg.ru"
            LoginDiaryActivity.Diary.DNEVNIKRU -> "dnevnik.ru"
            LoginDiaryActivity.Diary.NETSCHOOL -> "NetSchool"
        }
        loginTitle.text = "Войдите с помощью данных акканута $title"

        KeyboardVisibilityEvent.setEventListener(this) {
            binding.viewModel!!.keyboardIsShowing.set(it)
        }

        problemsWithLoggingButton.setOnClickListener {
            browse(getString(R.string.url_recover_password))
        }

        regulations.setOnClickListener {
            browse(getString(R.string.url_regulations))
        }

        privacyPolicy.setOnClickListener {
            browse(getString(R.string.url_privacy_policy))
        }

        loginFieldOutline.setEndIconOnClickListener {
            alert("Для входа в дневник вы можете использовать номер телефона или почту, " +
                    "указанные на сайте mos.ru.\n\n" +
                    "Вводите номер в формате +79... или 9...") {
                yesButton {}
            }.show()
        }

        binding.viewModel!!.authLiveData.observe(this, Observer {
            Log.d("Login", "Auth data received")
            stopLoading()
            val auth = it?.body()
            when {
                auth == null ->
                    onNetworkProblems()
                !it.isSuccessful ->
                    onUnknownError()
                auth.status == null ->
                    onUnknownError()
                !auth.status ->
                    onLoginFailed(auth.message)
                else ->
                    onLoginSuccessful(loginField.text.toString(),
                            passwordField.text.toString(),
                            auth.id!!,
                            auth.secret!!,
                            auth.students!!.list)
            }
        })
    }

    override fun onBackPressed() {
        setResult(MainActivity.RESULT_BACK_PRESSED)
        finish()
    }

    private fun onLoginSuccessful(login: String,
                                  password: String,
                                  pid: Long,
                                  secret: String,
                                  profiles: List<Student>?) {
        val prefs = Preferences.getInstance(this)
        val names = profiles!!.map { it.name.toString() }
        val ids = profiles.map { it.id!! }
        selector("Выберите аккаунт ребёнка:", names) { _, i ->
            prefs.userLogin = login
            prefs.userPassword = password
            prefs.userPid = pid
            prefs.userSecret = secret
            prefs.userStudentProfileId = ids[i].toString()
            prefs.userStudentProfiles = profiles
            setResult(MainActivity.RESULT_OK)
            finish()
        }
    }

    private fun onLoginFailed(text: String?) {
        alert(text ?: "Неверный логин или пароль.") { yesButton {} }
                .show()
    }

    private fun onNetworkProblems() {
        alert("Проверьте подключение к интернету.") { yesButton {} }
                .show()
    }

    private fun onUnknownError() {
        alert("Произошла ошибка. Попробуйте ещё раз.") { yesButton {} }
                .show()
    }

    protected fun startLoading() {
        loginEnterButton.animate()
                .translationY(56.px.toFloat())
                .duration = 60
    }

    protected fun stopLoading() {
        loginEnterButton.animate()
                .translationY(0f)
                .duration = 60
    }
}