package com.ssafy.achu.data.model.product

data class LikeProduct(
    val id: Int,
    val imgUrl: String,
    val price: Int,
    val title: String,
    val tradeStatus: String
)