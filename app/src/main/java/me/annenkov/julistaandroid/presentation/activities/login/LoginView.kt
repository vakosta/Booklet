package me.annenkov.julistaandroid.presentation.activities.login

interface LoginView {
    fun startLoading()
    fun endLoading()

    fun onLoginSuccessful()
    fun onLoginFailed()
    fun onNetworkProblems()
}