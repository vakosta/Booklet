package me.annenkov.julistaandroid.domain.repository

import android.content.Context

class AuthRepository(context: Context) : BaseRepository(context) {
    suspend fun auth(diary: String, login: String, password: String) =
            client.auth(diary, login, password)
}