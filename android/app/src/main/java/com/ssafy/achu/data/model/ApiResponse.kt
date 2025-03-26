package com.ssafy.achu.data.model

data class ApiResponse<T>(
    val result: String,
    val data: T,
    val error: Error
)

data class Error(
    val code: String,
    val message: String
)                                     