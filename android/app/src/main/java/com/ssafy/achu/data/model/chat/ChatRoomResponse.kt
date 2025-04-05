package com.ssafy.achu.data.model.chat

data class ChatRoomResponse(
    val buyerId: Int,
    val goods: Goods,
    val goodsId: Int,
    val id: Int,
    val isPartnerLeft: Boolean,
    val isBuyerLeft: Boolean,
    val isSellerLeft: Boolean,
    val lastMessage: Message,
    val partner: Partner,
    val sellerId: Int,
    val unreadCount: Int
)