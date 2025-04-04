package com.ssafy.achu.data.model.chat

data class ChatRoomResponse(
    val buyerId: Int,
    val goods: Goods,
    val goodsId: Int,
    val id: Int,
    val isBuyerLeft: Boolean,
    val isSellerLeft: Boolean,
    val lastMessage: LastMessage,
    val partner: Partner,
    val sellerId: Int
)