package com.ssafy.achu.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class MessageIdRequest(
    val lastReadMessageId: Int
)