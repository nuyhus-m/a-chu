package com.ssafy.achu.data.model.chat

data class ChatListResponse(
    val isUserSeller: Boolean,
    val partnerLastReadMessageId: Int,
    val chatRoom: ChatRoomResponse,
    val messages: List<Message>
)
