package com.booklet.bookletandroid.presentation.activities.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var diaryName: String

    private var regionId: Int? = null
    private var regionName: String? = null
        set(value) {
            field = value

            if (value != null) {
                netschoolRegion.findViewById<TextView>(R.id.infoText).text = value
                netschoolProvince.visibility = View.VISIBLE
            } else
                netschoolRegion.findViewById<TextView>(R.id.infoText).text = "Выберите регион"

            clearProvince()
            clearCity()
            clearSchool()
        }

    private var provinceId: Int? = null
    private var provinceName: String? = null
        set(value) {
            field = value

            if (value != null) {
                netschoolProvince.findViewById<TextView>(R.id.infoText).text = value
                netschoolCity.visibility = View.VISIBLE
            } else {
                netschoolProvince.findViewById<TextView>(R.id.infoText).text = "Выберите область"

                if (regionId == null)
                    netschoolProvince.visibility = View.GONE
            }

            clearCity()
            clearSchool()
        }

    private var cityId: Int? = null
    private var cityName: String? = null
        set(value) {
            field = value

            if (value != null) {
                netschoolCity.findViewById<TextView>(R.id.infoText).text = value
                netschoolSchool.visibility = View.VISIBLE
            } else {
                netschoolCity.findViewById<TextView>(R.id.infoText).text = "Выберите город"

                if (provinceId == null)
                    netschoolCity.visibility = View.GONE
            }

            clearSchool()
        }

    private var schoolId: Int? = null
    private var schoolName: String? = null
        set(value) {
            field = value

            if (value != null) {
                netschoolSchool.findViewById<TextView>(R.id.infoText).text = value
            } else {
                netschoolSchool.findViewById<TextView>(R.id.infoText).text = "Выберите школу"

                if (cityId == null)
                    netschoolSchool.visibility = View.GONE
            }
        }

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

        if (diary == LoginDiaryActivity.Diary.NETSCHOOL)
            netschoolRegion.visibility = View.VISIBLE

        netschoolRegion.setOnClickListener {
            clearRegion()
            netschoolRegion.findViewById<TextView>(R.id.infoText).text = "Загрузка..."
            binding.viewModel!!.getNetschoolData(regionId,
                    provinceId,
                    cityId)
        }
        netschoolProvince.setOnClickListener {
            clearProvince()
            netschoolProvince.findViewById<TextView>(R.id.infoText).text = "Загрузка..."
            binding.viewModel!!.getNetschoolData(regionId,
                    provinceId,
                    cityId)
        }
        netschoolCity.setOnClickListener {
            clearCity()
            netschoolCity.findViewById<TextView>(R.id.infoText).text = "Загрузка..."
            binding.viewModel!!.getNetschoolData(regionId,
                    provinceId,
                    cityId)
        }
        netschoolSchool.setOnClickListener {
            clearSchool()
            netschoolSchool.findViewById<TextView>(R.id.infoText).text = "Загрузка..."
            binding.viewModel!!.getNetschoolData(regionId,
                    provinceId,
                    cityId)
        }

        KeyboardVisibilityEvent.setEventListener(this) {
            binding.viewModel!!.keyboardIsShowing.set(it)
        }

        loginEnterButton.setOnClickListener {
            startLoading()
            binding.viewModel!!.doAuth(diaryName,
                    loginField.text.toString(),
                    passwordField.text.toString(),
                    regionId,
                    provinceId,
                    cityId,
                    schoolId)
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

        binding.viewModel!!.netschoolRegionLiveData.observe(this, Observer { response ->
            val data = response?.body()!!.data!!
            val countries = data.map { it.name.toString() }
            selector("Выберите ваш регион", countries) { _, i ->
                regionId = data[i].id
                regionName = data[i].name
            }
        })

        binding.viewModel!!.netschoolProvinceLiveData.observe(this, Observer { response ->
            val data = response?.body()!!.data!!
            val countries = data.map { it.name.toString() }
            selector("Выберите вашу область", countries) { _, i ->
                provinceId = data[i].id
                provinceName = data[i].name
            }
        })

        binding.viewModel!!.netschoolCityLiveData.observe(this, Observer { response ->
            val data = response?.body()!!.data!!
            val countries = data.map { it.name.toString() }
            selector("Выберите ваш город", countries) { _, i ->
                cityId = data[i].id
                cityName = data[i].name
            }
        })

        binding.viewModel!!.netschoolSchoolLiveData.observe(this, Observer { response ->
            val data = response?.body()!!.data!!
            val countries = data.map { it.name.toString() }
            selector("Выберите ваш регион", countries) { _, i ->
                schoolId = data[i].id
                schoolName = data[i].name
            }
        })
    }

    private fun clearRegion() {
        regionId = null
        regionName = null
    }

    private fun clearProvince() {
        provinceId = null
        provinceName = null
    }

    private fun clearCity() {
        cityId = null
        cityName = null
    }

    private fun clearSchool() {
        schoolId = null
        schoolName = null
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