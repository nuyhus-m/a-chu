package com.ssafy.achu.data.model.auth

data class SignupRequest(
    val nickname: String,
    val password: String,
    val phoneNumber: String,
    val username: String,
    val verificationCodeId: String
)