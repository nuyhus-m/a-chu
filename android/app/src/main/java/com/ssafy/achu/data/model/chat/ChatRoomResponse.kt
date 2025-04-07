package com.ssafy.achu.data.model.chat

data class ChatRoomResponse(
    val goods: Goods,
    val id: Int,
    val isPartnerLeft: Boolean,
    val lastMessage: Message,
    val partner: Partner,
    val unreadCount: Int
)