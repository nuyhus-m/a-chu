package com.ssafy.achu.data.model.auth

data class SignUpRequest(
    val nickname: String,
    val password: String,
    val phoneNumber: String,
    val username: String,
    val verificationCodeId: String
)