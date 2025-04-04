package com.ssafy.achu.data.repository

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.Token
import com.ssafy.achu.data.network.RetrofitUtil

class FcmRepository {

    private val fcmService = RetrofitUtil.fcmService


    // 토큰 등록
    suspend fun updateToken(token: Token): Result<ApiResult<Unit>> {
        return runCatching {
            fcmService.updateToken(token)
        }
    }

    // 토큰 삭제
    suspend fun deleteToken(): Result<ApiResult<Unit>> {
        return runCatching {
            fcmService.deleteToken()
        }
    }
}