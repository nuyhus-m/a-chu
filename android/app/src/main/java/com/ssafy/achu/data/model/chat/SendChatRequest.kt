package com.ssafy.achu.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class SendChatRequest(
    val content: String,
    val type: String
)