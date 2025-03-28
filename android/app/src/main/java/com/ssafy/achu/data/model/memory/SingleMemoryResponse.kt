package com.ssafy.achu.data.model.memory

data class SingleMemoryResponse(
    val content: String,
    val createdAt: String,
    val id: Int,
    val imgUrls: List<String>,
    val title: String,
    val updatedAt: String
)