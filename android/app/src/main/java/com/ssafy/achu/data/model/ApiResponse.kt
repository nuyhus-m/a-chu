package com.ssafy.achu.data.model

data class ApiResponse<T>(
    val result: String,
    val data: T
)