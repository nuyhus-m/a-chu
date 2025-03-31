package com.ssafy.achu.core

import android.util.Log
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import okhttp3.Interceptor

private const val TAG = "TokenInterceptor"

class TokenInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        val token = sharedPreferencesUtil.getTokens() ?: return chain.proceed(chain.request())
        val originalRequest = chain.request()
        val isNeedAccessToken = shouldAddToken(originalRequest)
        
        // 헤더를 추가할지 결정하는 로직
        val newRequest = if (isNeedAccessToken) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "${token.tokenType} ${token.accessToken}")
                .build()
        } else {
            originalRequest
        }

        Log.d(
            TAG,
            "intercept addHeader:${isNeedAccessToken} url:${originalRequest.url}"
        )

        return chain.proceed(newRequest)
    }

    // 헤더를 추가해야 하는 API 판단 로직
    private fun shouldAddToken(request: okhttp3.Request): Boolean {
        val noTokenUrls = listOf(
            "/auth/login",
            "/auth/token/reissue",
            "/auth/token/renew",
            "/users",
            "/users/username/is-unique",
            "/users/nickname/is-unique",
            "/verification/request",
            "/verification/verify",
            "/users/password/reset"
        )

        val isNoTokenUrl = noTokenUrls.any {
            request.url.toString().endsWith(it)
        }

        return !isNoTokenUrl
    }
}