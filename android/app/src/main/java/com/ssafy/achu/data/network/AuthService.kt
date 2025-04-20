package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.auth.AccessTokenResponse
import com.ssafy.achu.data.model.auth.RefreshToken
import com.ssafy.achu.data.model.auth.SignInRequest
import com.ssafy.achu.data.model.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/login")
    suspend fun signIn(@Body signInRequest: SignInRequest): ApiResult<TokenResponse>

    @POST("/auth/token/reissue")
    suspend fun refreshAccessToken(@Body refreshToken: RefreshToken): ApiResult<AccessTokenResponse>

    @POST("/auth/token/renew")
    suspend fun renewTokens(@Body refreshToken: RefreshToken): ApiResult<TokenResponse>

}