package com.ssafy.achu.core

import okhttp3.Interceptor

private const val TAG = "TokenInterceptor"

class TokenInterceptor(private val accessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()

        // 헤더를 추가할지 결정하는 로직
        val newRequest = if (shouldAddToken(originalRequest)) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else {
            originalRequest
        }

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