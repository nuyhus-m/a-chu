package com.ssafy.achu.data.model.auth

data class ChangePhoneNumberRequest(
    val phoneNumber: String,
    val verificationCodeId: String
)