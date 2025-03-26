package com.ssafy.achu.data.model.auth

data class AccessTokenResponse(
    val accessToken: String,
    val accessTokenExpiresIn: Int,
    val tokenType: String
)