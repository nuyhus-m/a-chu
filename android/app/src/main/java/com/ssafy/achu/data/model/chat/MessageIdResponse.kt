package com.ssafy.achu.data.model.chat

data class MessageIdResponse(
    val lastUnreadMessageId: Int,
    val userId: Int
)