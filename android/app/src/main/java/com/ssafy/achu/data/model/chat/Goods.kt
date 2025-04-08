package com.ssafy.achu.data.model.chat

import kotlinx.serialization.Serializable

@Serializable
data class Goods(
    val id: Int,
    val thumbnailImageUrl: String,
    val title: String,
    val price: Long,
    val tradeStatus: String,
)