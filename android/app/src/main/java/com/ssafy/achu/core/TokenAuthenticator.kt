package com.ssafy.achu.core

import android.util.Log
import com.ssafy.achu.core.ApplicationClass.Companion.authRepository
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.data.model.auth.RefreshToken
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

private const val TAG = "TokenAuthenticator"

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            if (response.code != 401) return@runBlocking null

            val tokens = sharedPreferencesUtil.getTokens() ?: return@runBlocking null
            val tokenType = tokens.tokenType
            val refreshToken = tokens.refreshToken

            val newRequest =
                authRepository.refreshAccessToken(RefreshToken("$tokenType $refreshToken"))
                    .mapCatching {
                        Log.d(TAG, "Authorization: $it")
                        if (it.result == SUCCESS) {
                            val accessTokenResponse = it.data
                            val issuedAt = System.currentTimeMillis() / 1000
                            sharedPreferencesUtil.saveAccessTokenIssuedAt(issuedAt)
                            sharedPreferencesUtil.updateAccessToken(accessTokenResponse)

                            // 새로운 Access Token으로 요청 재시도
                            response.request.newBuilder()
                                .header(
                                    "Authorization",
                                    "${accessTokenResponse.tokenType} ${accessTokenResponse.accessToken}"
                                ).build()
                        } else {
                            Log.e(TAG, "authenticate: ${it.result}")
                            null
                        }
                    }.getOrElse {
                        Log.e(TAG, "authenticate: ${it.message}", it)
                        null
                    }

            return@runBlocking newRequest
        }
    }
}