package com.ssafy.achu.data.model.chat

data class LastMessage(
    val content: String,
    val id: Int,
    val senderId: Int,
    val timestamp: String,
    val type: String
)