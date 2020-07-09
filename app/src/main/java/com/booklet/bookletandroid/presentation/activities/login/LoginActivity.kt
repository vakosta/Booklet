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
import com.booklet.bookletandroid.domain.model.Result
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

        setupUI()
        setupListeners()
        setupObservers()
    }

    private fun setupUI() {
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
    }

    private fun setupListeners() {
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

        loginEnterButton.setOnClickListener {
            startLoading()
            binding.viewModel!!.doAuth(diaryName,
                    loginField.text.toString(),
                    passwordField.text.toString())
        }
    }

    private fun setupObservers() {
        binding.viewModel!!.authLiveData.observe(this, Observer {
            it?.let { result ->
                when (result.status) {
                    Result.Status.SUCCESS -> {
                        Log.d(TAG, "Успешная авторизация.")
                        stopLoading()

                        onLoginSuccessful(loginField.text.toString(),
                                passwordField.text.toString(),
                                result.data!!.id!!,
                                result.data.secret!!,
                                result.data.students!!.list)
                    }
                    Result.Status.ERROR -> {
                        Log.d(TAG, "Ошибка при авторизации.")
                        stopLoading()

                        onUnknownError()
                    }
                    Result.Status.LOADING -> {
                        Log.d(TAG, "Начался запрос авторизации.")
                        startLoading()
                    }
                }
            }
        })
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

    override fun onBackPressed() {
        setResult(MainActivity.RESULT_BACK_PRESSED)
        finish()
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

    companion object {
        private val TAG = this::class.java.simpleName
    }
}