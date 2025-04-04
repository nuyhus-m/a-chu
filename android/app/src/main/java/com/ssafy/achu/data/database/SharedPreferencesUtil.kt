package com.ssafy.achu.data.database

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.ssafy.achu.data.model.auth.AccessTokenResponse
import com.ssafy.achu.data.model.auth.TokenResponse

class SharedPreferencesUtil(context: Context) {

    companion object {
        private const val SHARED_PREFERENCES_NAME = "achu_preference"
        private const val KEY_IS_AUTO_LOGIN = "is_auto_login"
    }

    private var preferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveAccessTokenIssuedAt(timestamp: Long) {
        preferences.edit {
            putLong("access_issued_at", timestamp)
        }
    }

    fun getAccessTokenIssuedAt(): Long {
        return preferences.getLong("access_issued_at", 0)
    }

    fun saveRefreshTokenIssuedAt(timestamp: Long) {
        preferences.edit {
            putLong("refresh_issued_at", timestamp)
        }
    }

    fun getRefreshTokenIssuedAt(): Long {
        return preferences.getLong("refresh_issued_at", 0)
    }

    fun saveTokens(tokenResponse: TokenResponse) {
        preferences.edit {
            val gson = Gson()
            val json = gson.toJson(tokenResponse)  // 객체를 JSON 문자열로 변환
            putString("token_data", json)
        }
    }

    fun getTokens(): TokenResponse? {
        val gson = Gson()
        val json = preferences.getString("token_data", null)
        return json?.let { gson.fromJson(it, TokenResponse::class.java) }  // JSON 문자열을 객체로 변환
    }

    fun updateAccessToken(accessTokenResponse: AccessTokenResponse) {
        val tokenResponse = getTokens()

        if (tokenResponse != null) {
            val updatedTokenResponse = tokenResponse.copy(
                tokenType = accessTokenResponse.tokenType,
                accessToken = accessTokenResponse.accessToken,
                accessTokenExpiresIn = accessTokenResponse.accessTokenExpiresIn
            )

            saveTokens(updatedTokenResponse)
        }
    }


    fun clearTokensInfo() {
        preferences.edit {
            remove("token_data")
            remove("access_issued_at")
            remove("refresh_issued_at")
        }
    }

    fun saveIsAutoLogin(isEnabled: Boolean) {
        preferences.edit {
            putBoolean(KEY_IS_AUTO_LOGIN, isEnabled)
        }
    }

    fun isAutoLogin(): Boolean {
        return preferences.getBoolean(KEY_IS_AUTO_LOGIN, true)
    }

}