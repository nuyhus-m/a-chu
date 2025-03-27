package com.ssafy.achu.data.model.product

data class ProductResponse(
    val chatCount: Int,
    val createdAt: String,
    val id: Int,
    val imgUrl: String,
    var likedByUser: Boolean,
    val likedUsersCount: Int,
    val price: Int,
    val title: String
)