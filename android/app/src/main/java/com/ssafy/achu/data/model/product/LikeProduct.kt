package com.ssafy.achu.data.model.product

data class LikeProduct(
    val id: Int,
    val imgUrl: String,
    val price: Long,
    val title: String,
    val tradeStatus: String
)