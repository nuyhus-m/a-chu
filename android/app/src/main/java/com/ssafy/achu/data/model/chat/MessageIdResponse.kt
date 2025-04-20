package com.ssafy.achu.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class MessageIdResponse(
    val lastUnreadMessageId: Int,
    val userId: Int
)