package me.annenkov.julistaandroid.presentation.activities.login

import android.content.Context

class LoginPresenter(
        private val view: LoginView,
        private val mContext: Context
) {
    fun login() {
        view.startLoading()
    }
}