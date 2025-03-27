package com.ssafy.achu.data.model

data class ApiResult<T>(
    val result: String,
    val data: T
)