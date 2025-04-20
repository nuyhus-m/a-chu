package com.ssafy.achu.data.model.auth

data class CheckPhoneAuthRequest(
    val code: String,
    val id: String
)