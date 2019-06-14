package me.annenkov.julistaandroid.presentation.activities.login

import me.annenkov.julistaandroid.data.model.booklet.students.Student

interface LoginView {
    fun startLoading()
    fun endLoading()

    fun onLoginSuccessful(login: String, password: String, secret: String, profiles: List<Student>?)
    fun onLoginFailed()
    fun onNetworkProblems()
}