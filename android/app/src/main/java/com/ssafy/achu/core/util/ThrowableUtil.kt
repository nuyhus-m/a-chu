package com.ssafy.achu.core.util

import com.ssafy.achu.data.model.ErrorDetail
import com.ssafy.achu.data.model.ErrorResponse
import retrofit2.HttpException
import retrofit2.Retrofit

fun Throwable.getErrorResponse(retrofit: Retrofit): ErrorDetail {
    if (this is HttpException) {
        val errorBody = this.response()?.errorBody()

        // Gson 컨버터를 사용한 에러 바디 파싱
        val errorAdapter = retrofit.responseBodyConverter<ErrorResponse>(
            ErrorResponse::class.java,
            arrayOfNulls(0)
        )

        return try {
            errorBody?.let {
                val parsedError = errorAdapter.convert(it)
                val code = parsedError?.error?.code ?: "0"
                val message = parsedError?.error?.message ?: "알 수 없는 오류가 발생했습니다."
                ErrorDetail(code, message)
            } ?: ErrorDetail("0", "알 수 없는 오류가 발생했습니다.")
        } catch (e: Exception) {
            // 파싱 실패 시 기본 메시지
            ErrorDetail("1", "알 수 없는 오류가 발생했습니다.")
        }
    }

    // 네트워크 등 다른 예외의 경우
    return ErrorDetail("2", "알 수 없는 오류가 발생했습니다.")
}