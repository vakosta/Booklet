package me.annenkov.julistaandroid.presentation.activities.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import kotterknife.bindView
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.data.model.julista.auth.Profile
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.activities.main.MainActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.browse
import org.jetbrains.anko.selector
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
        mPresenter = LoginPresenter(this, this)

        mLoginEnterButton.setOnClickListener {
            mPresenter.login(mLoginField.text.toString(),
                    mPasswordField.text.toString())
        }

        mProblemsWithLoggingButton.setOnClickListener {
            browse(getString(R.string.url_recover_password))
        }

        mContactButton.setOnClickListener {
            browse(getString(R.string.url_vk_page))
        }

        infoButton.setOnClickListener { _ ->
            alert("Для входа в дневник вы можете использовать номер телефона или почту, " +
                    "указанные на сайте mos.ru.\n\n" +
                    "Вводите номер в формате +79... или 9...") {
                yesButton {}
            }.show()
        }
    }

    override fun onBackPressed() {
        setResult(MainActivity.RESULT_BACK_PRESSED)
        finish()
    }

    override fun onLoginSuccessful(login: String,
                                   password: String,
                                   token: String,
                                   pid: String,
                                   studentProfileId: String,
                                   botCode: String,
                                   profiles: List<Profile>?) {
        endLoading()
        val prefs = Preferences.getInstance(this)
        val names = profiles!!.map { it.name.toString() }
        val ids = profiles.map { it.studentProfileId!! }
        selector("Выберите аккаунт ребёнка:", names) { _, i ->
            prefs.userLogin = login
            prefs.userPassword = password
            prefs.userToken = token
            prefs.userPid = pid
            prefs.userStudentProfileId = ids[i].toString()
            prefs.botCode = botCode
            prefs.userStudentProfiles = profiles
            setResult(MainActivity.RESULT_OK)
            finish()
        }
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