package com.ssafy.achu.data.model.auth

data class SignInRequest(
    val password: String,
    val username: String
)