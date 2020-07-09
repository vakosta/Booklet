package com.booklet.bookletandroid.domain.repository

import android.content.Context
import com.booklet.bookletandroid.domain.ApiHelper

abstract class BaseRepository(context: Context) {
    var client = ApiHelper.getInstance(context)
            .getAPI()

    companion object {
        private val TAG = this::class.java.simpleName
    }
}