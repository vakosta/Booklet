package me.annenkov.julistaandroid.domain.repository

import android.content.Context
import me.annenkov.julistaandroid.domain.ApiHelper

abstract class BaseRepository(context: Context) {
    var client = ApiHelper.getInstance(context)
            .getAPI(ApiHelper.ApiType.BOOKLET)
}