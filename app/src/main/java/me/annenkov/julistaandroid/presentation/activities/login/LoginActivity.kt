package me.annenkov.julistaandroid.presentation.activities.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotterknife.bindView
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.data.model.julista.auth.Profile
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.activities.main.MainActivity
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.jetbrains.anko.alert
import org.jetbrains.anko.browse
import org.jetbrains.anko.selector
import org.jetbrains.anko.yesButton

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var mPresenter: LoginPresenter

    private val mLoginField: EditText by bindView(R.id.loginField)
    private val mPasswordField: EditText by bindView(R.id.passwordField)
    private val mLoginEnterButton: FrameLayout by bindView(R.id.loginEnterButton)
    private val mProblemsWithLoggingButton: LinearLayout by bindView(R.id.problemsWithLoggingButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPresenter = LoginPresenter(this, this)

        KeyboardVisibilityEvent.setEventListener(this) {
            if (it) {
                header.visibility = View.GONE
                privacyBlock.visibility = View.GONE
                problemsWithLoggingButton.visibility = View.GONE
            } else {
                header.visibility = View.VISIBLE
                privacyBlock.visibility = View.VISIBLE
                problemsWithLoggingButton.visibility = View.VISIBLE
            }
        }

        mLoginEnterButton.setOnClickListener {
            mPresenter.login(mLoginField.text.toString(),
                    mPasswordField.text.toString(),
                    inviteCodeField.text.toString())
        }

        mProblemsWithLoggingButton.setOnClickListener {
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
                                   inviteCode: String,
                                   invitations: Int,
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
            prefs.inviteCode = inviteCode
            prefs.invitations = invitations
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
        mLoginEnterButton.animate()
                .translationY(56.px.toFloat())
                .duration = 60
    }

    override fun endLoading() {
        mLoginEnterButton.animate()
                .translationY(0f)
                .duration = 60
    }
}