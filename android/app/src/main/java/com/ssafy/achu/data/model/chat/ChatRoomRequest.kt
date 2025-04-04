package com.ssafy.achu.data.model.chat

data class ChatRoomRequest(
    val content: String,
    val goodsId: Int,
    val sellerId: Int
)