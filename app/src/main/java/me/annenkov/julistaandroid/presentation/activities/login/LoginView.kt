package me.annenkov.julistaandroid.presentation.activities.login

import me.annenkov.julistaandroid.data.model.booklet.auth.Student

interface LoginView {
    fun startLoading()
    fun stopLoading()

    fun onLoginSuccessful(login: String, password: String, secret: String, profiles: List<Student>?)
    fun onLoginFailed(text: String?)
    fun onNetworkProblems()
    fun onUnknownError()
}