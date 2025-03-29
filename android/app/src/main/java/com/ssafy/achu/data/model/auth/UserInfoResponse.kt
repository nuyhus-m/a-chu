package com.ssafy.achu.data.model.auth

data class UserInfoResponse(
    val id: Int,
    val username: String,
    val nickname: String,
    var profileImageUrl: String,
    val phoneNumber: String,
)