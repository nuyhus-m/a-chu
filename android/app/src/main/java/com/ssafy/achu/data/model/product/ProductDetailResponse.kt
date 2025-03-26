package com.ssafy.achu.data.model.product

data class ProductDetailResponse(
    val category: Category,
    val createdAt: String,
    val description: String,
    val id: Int,
    val imgUrls: List<String>,
    val likedByUser: Boolean,
    val likedUsersCount: Int,
    val price: Int,
    val seller: Seller,
    val title: String,
    val tradeStatus: String
)