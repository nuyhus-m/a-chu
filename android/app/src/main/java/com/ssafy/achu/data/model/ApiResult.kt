package com.ssafy.achu.data.model

data class ApiResult<T>(
    val result: String,
    val data: T
)

// 에러 응답 데이터 클래스
data class ErrorResponse(
    val result: String,
    val error: ErrorDetail
)

data class ErrorDetail(
    val code: String,
    val message: String
)