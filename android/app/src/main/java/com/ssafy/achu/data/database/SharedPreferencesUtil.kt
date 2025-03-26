package com.ssafy.achu.data.database

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "achu_preference"
        private const val COOKIES_KEY_NAME = "cookies"
    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

}