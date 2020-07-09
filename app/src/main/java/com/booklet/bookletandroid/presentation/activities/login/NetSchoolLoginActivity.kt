package com.booklet.bookletandroid.presentation.activities.login

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.booklet.bookletandroid.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.selector

class NetSchoolLoginActivity : LoginActivity() {
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

        netschoolRegion.visibility = View.VISIBLE

        netschoolRegion.setOnClickListener {
            clearRegion()
            netschoolRegion.findViewById<TextView>(R.id.infoText).text = "Загрузка..."
            super.binding.viewModel!!.getNetschoolData(regionId,
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

        binding.viewModel!!.netschoolRegionLiveData.observe(this, Observer { response ->
            val data = response?.data!!.data!!
            val countries = data.map { it.name.toString() }
            selector("Выберите ваш регион", countries) { _, i ->
                regionId = data[i].id
                regionName = data[i].name
            }
        })

        binding.viewModel!!.netschoolProvinceLiveData.observe(this, Observer { response ->
            val data = response?.data!!.data!!
            val countries = data.map { it.name.toString() }
            selector("Выберите вашу область", countries) { _, i ->
                provinceId = data[i].id
                provinceName = data[i].name
            }
        })

        binding.viewModel!!.netschoolCityLiveData.observe(this, Observer { response ->
            val data = response?.data!!.data!!
            val countries = data.map { it.name.toString() }
            selector("Выберите ваш город", countries) { _, i ->
                cityId = data[i].id
                cityName = data[i].name
            }
        })

        binding.viewModel!!.netschoolSchoolLiveData.observe(this, Observer { response ->
            val data = response?.data!!.data!!
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
}