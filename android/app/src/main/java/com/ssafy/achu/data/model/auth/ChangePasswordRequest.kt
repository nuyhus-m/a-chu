package com.ssafy.achu.data.model.auth

data class ChangePasswordRequest(
    val newPassword: String,
    val oldPassword: String
)