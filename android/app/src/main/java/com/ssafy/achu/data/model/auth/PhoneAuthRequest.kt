package com.ssafy.achu.data.model.auth

data class PhoneAuthRequest(
    val phoneNumber: String,
    val purpose: String
)