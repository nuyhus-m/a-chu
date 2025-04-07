package com.ssafy.achu.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val content: String,
    val id: Int,
    val senderId: Int,
    val timestamp: String,
    val type: String,
    val isMine: Boolean = false
)