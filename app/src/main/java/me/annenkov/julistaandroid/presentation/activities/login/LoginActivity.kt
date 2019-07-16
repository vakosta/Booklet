package me.annenkov.julistaandroid.presentation.activities.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.data.model.booklet.auth.Student
import me.annenkov.julistaandroid.databinding.ActivityLoginBinding
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.activities.main.MainActivity
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.jetbrains.anko.alert
import org.jetbrains.anko.browse
import org.jetbrains.anko.selector
import org.jetbrains.anko.yesButton

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.executePendingBindings()

        KeyboardVisibilityEvent.setEventListener(this) {
            binding.viewModel!!.keyboardIsShowing.set(it)
        }

        loginEnterButton.setOnClickListener {
            startLoading()
            binding.viewModel!!.doAuth("mosru",
                    loginField.text.toString(),
                    passwordField.text.toString())
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

        infoButton.setOnClickListener {
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

    private fun startLoading() {
        loginEnterButton.animate()
                .translationY(56.px.toFloat())
                .duration = 60
    }

    private fun stopLoading() {
        loginEnterButton.animate()
                .translationY(0f)
                .duration = 60
    }
}