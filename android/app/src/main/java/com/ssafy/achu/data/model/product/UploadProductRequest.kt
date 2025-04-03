package com.ssafy.achu.data.model.product

data class UploadProductRequest(
    val babyId: Int,
    val categoryId: Int,
    val description: String,
    val price: Int,
    val title: String
)