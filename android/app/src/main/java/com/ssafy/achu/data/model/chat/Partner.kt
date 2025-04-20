package com.ssafy.achu.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class Partner(
    val id: Int,
    val nickname: String,
    val profileImageUrl: String?
)