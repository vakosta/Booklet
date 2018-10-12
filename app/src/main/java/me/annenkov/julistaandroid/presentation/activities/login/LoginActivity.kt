package me.annenkov.julistaandroid.presentation.activities.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import kotterknife.bindView
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.domain.Utils
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.activities.main.MainActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.browse
import org.jetbrains.anko.yesButton

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var mPresenter: LoginPresenter

    private val mLoginField: EditText by bindView(R.id.loginField)
    private val mPasswordField: EditText by bindView(R.id.passwordField)
    private val mLoginEnterButton: Button by bindView(R.id.loginEnterButton)
    private val mProblemsWithLoggingButton: TextView by bindView(R.id.problemsWithLoggingButton)
    private val mContactButton: TextView by bindView(R.id.contactButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPresenter = LoginPresenter(this)

        mLoginEnterButton.setOnClickListener {
            mPresenter.login(this,
                    mLoginField.text.toString(),
                    mPasswordField.text.toString())
        }

        mProblemsWithLoggingButton.setOnClickListener {
            browse(getString(R.string.url_recover_password))
        }

        mContactButton.setOnClickListener {
            browse(getString(R.string.url_vk_page))
        }
    }

    override fun onBackPressed() {
        setResult(MainActivity.RESULT_BACK_PRESSED)
        finish()
    }

    override fun onLoginSuccessful() {
        setResult(MainActivity.RESULT_OK)
        finish()
    }

    override fun onLoginFailed() {
        alert("Неправильный логин или пароль") {
            yesButton {}
        }.show()

        endLoading()
    }

    override fun onNetworkProblems() {
        alert("Проверьте подключение к интернету") {
            yesButton {}
        }.show()

        endLoading()
    }

    override fun startLoading() {
        loginEnterButton.animate()
                .translationY(56.px.toFloat())
                .duration = 60
    }

    override fun endLoading() {
        loginEnterButton.animate()
                .translationY(0f)
                .duration = 60
    }
}