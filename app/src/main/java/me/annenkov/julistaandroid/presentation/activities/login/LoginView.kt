package me.annenkov.julistaandroid.presentation.activities.login

import me.annenkov.julistaandroid.data.model.julista.auth.Profile

interface LoginView {
    fun startLoading()
    fun endLoading()

    fun onLoginSuccessful(login: String, password: String, token: String, pid: String, studentProfileId: String, botCode: String, profiles: List<Profile>?)
    fun onLoginFailed()
    fun onNetworkProblems()
}