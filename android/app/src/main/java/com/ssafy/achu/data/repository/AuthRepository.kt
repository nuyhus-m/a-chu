package com.ssafy.achu.data.repository

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.auth.AccessTokenResponse
import com.ssafy.achu.data.model.auth.RefreshToken
import com.ssafy.achu.data.model.auth.SignInRequest
import com.ssafy.achu.data.model.auth.TokenResponse
import com.ssafy.achu.data.network.RetrofitUtil

class AuthRepository {

    private val authService = RetrofitUtil.authService

    suspend fun signIn(signInRequest: SignInRequest): Result<ApiResult<TokenResponse>> {
        return kotlin.runCatching {
            authService.signIn(signInRequest)
        }
    }

    suspend fun refreshAccessToken(refreshToken: RefreshToken): Result<ApiResult<AccessTokenResponse>> {
        return kotlin.runCatching {
            authService.refreshAccessToken(refreshToken)
        }
    }

    suspend fun renewTokens(refreshToken: RefreshToken): Result<ApiResult<TokenResponse>> {
        return kotlin.runCatching {
            authService.renewTokens(refreshToken)
        }
    }
}