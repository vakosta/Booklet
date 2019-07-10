package me.annenkov.julistaandroid.presentation.activities.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotterknife.bindView
import me.annenkov.julistaandroid.R
import me.annenkov.julistaandroid.data.model.booklet.auth.Student
import me.annenkov.julistaandroid.domain.Preferences
import me.annenkov.julistaandroid.domain.TestViewModel
import me.annenkov.julistaandroid.domain.px
import me.annenkov.julistaandroid.presentation.activities.main.MainActivity
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.jetbrains.anko.alert
import org.jetbrains.anko.browse
import org.jetbrains.anko.selector
import org.jetbrains.anko.yesButton

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var mPresenter: LoginPresenter
    private lateinit var tmdbViewModel: TestViewModel

    private val mLoginField: EditText by bindView(R.id.loginField)
    private val mPasswordField: EditText by bindView(R.id.passwordField)
    private val mLoginEnterButton: FrameLayout by bindView(R.id.loginEnterButton)
    private val mProblemsWithLoggingButton: LinearLayout by bindView(R.id.problemsWithLoggingButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPresenter = LoginPresenter(this, this)

        tmdbViewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)

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
            startLoading()
            tmdbViewModel.doAuth("mosru",
                    mLoginField.text.toString(),
                    mPasswordField.text.toString())
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

        tmdbViewModel.popularMoviesLiveData.observe(this, Observer {
            endLoading()
            onLoginSuccessful(mLoginField.text.toString(),
                    mPasswordField.text.toString(),
                    it!!.secret!!,
                    it.students!!.list)
            Log.d("Login", "Auth data received")
        })
    }

    override fun onBackPressed() {
        setResult(MainActivity.RESULT_BACK_PRESSED)
        finish()
    }

    override fun onLoginSuccessful(login: String,
                                   password: String,
                                   secret: String,
                                   profiles: List<Student>?) {
        endLoading()
        val prefs = Preferences.getInstance(this)
        val names = profiles!!.map { it.name.toString() }
        val ids = profiles.map { it.id!! }
        selector("Выберите аккаунт ребёнка:", names) { _, i ->
            prefs.userLogin = login
            prefs.userPassword = password
            prefs.userSecret = secret
            prefs.userStudentProfileId = ids[i].toString()
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