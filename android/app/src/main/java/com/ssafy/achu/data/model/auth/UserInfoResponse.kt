package com.ssafy.achu.data.model.auth

data class UserInfoResponse(
    var imageUrl: String,
    val nickname: String,
    val userId: String,
    val phoneNumber: String,
)