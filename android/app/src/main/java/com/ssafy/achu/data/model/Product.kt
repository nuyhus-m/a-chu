package com.ssafy.achu.data.model

data class Product(
    val chatCount: Int,
    val createdAt: String,
    val id: Int,
    val imgUrl: String,
    val likedByUser: Boolean,
    val likedUsersCount: Int,
    val price: Int,
    val title: String
)