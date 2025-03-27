package com.ssafy.achu.data.database

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.ssafy.achu.data.model.auth.TokenResponse

class SharedPreferencesUtil(context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "achu_preference"
    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveTokens(tokenResponse: TokenResponse) {
        preferences.edit {
            val gson = Gson()
            val json = gson.toJson(tokenResponse)  // 객체를 JSON 문자열로 변환
            putString("token_data", json)
        }
    }

    fun getTokens(context: Context): TokenResponse? {
        val gson = Gson()
        val json = preferences.getString("token_data", null)
        return json?.let { gson.fromJson(it, TokenResponse::class.java) }  // JSON 문자열을 객체로 변환
    }
}