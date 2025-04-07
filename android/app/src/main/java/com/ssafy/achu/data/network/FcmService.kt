package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.Token
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface FcmService {

    @POST("/fcm-token")
    suspend fun updateToken(@Body token: Token): ApiResult<Unit>

    @DELETE("/fcm-token")
    suspend fun deleteToken(): ApiResult<Unit>

}