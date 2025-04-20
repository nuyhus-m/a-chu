package com.ssafy.achu.data.model.auth

data class TokenResponse(
    val accessToken: String,
    val accessTokenExpiresIn: Int,
    val refreshToken: String,
    val refreshTokenExpiresIn: Int,
    val refreshTokenRenewAvailableSeconds: Int,
    val tokenType: String
)