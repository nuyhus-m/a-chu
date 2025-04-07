package com.ssafy.achu.data.model.product

data class ModifyProductRequest(
    val categoryId: Int,
    val description: String,
    val price: Long,
    val title: String
)