package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.memory.MemoryRequest
import com.ssafy.achu.data.model.memory.MemoryResponse
import com.ssafy.achu.data.model.memory.SingleMemoryResponse
import okhttp3.MultipartBody
import retrofit2.http.*

private const val TAG = "MemoryService 추억"
interface MemoryService {

    // 추억 생성
    @Multipart
    @POST("/babies/{babyId}/memories")
    suspend fun createMemory(
        @Path("babyId") babyId: Int,
        @Part memoryImages: List<MultipartBody.Part>,
        @Part("request") request: MemoryRequest
    ): ApiResult<IdResponse>

    // 특정 추억 조회
    @GET("/memories/{memoryId}")
    suspend fun getMemory(@Path("memoryId") memoryId: Int): ApiResult<SingleMemoryResponse>

    // 특정 자녀의 추억 목록 조회
    @GET("/babies/{babyId}/memories")
    suspend fun getMemories(
        @Path("babyId") babyId: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String = "LATEST"
    ): ApiResult<List<MemoryResponse>>

    // 추억 사진 변경
    @Multipart
    @PATCH("/memories/{memoryId}/image")
    suspend fun updateMemoryImages(
        @Path("memoryId") memoryId: Int,
        @Part memoryImages: List<MultipartBody.Part>
    ): ApiResult<Unit>

    // 추억 정보 변경
    @PATCH("/memories/{memoryId}")
    suspend fun updateMemory(
        @Path("memoryId") memoryId: Int,
        @Body request: MemoryRequest
    ): ApiResult<Unit>

    // 추억 삭제
    @DELETE("/memories/{memoryId}")
    suspend fun deleteMemory(@Path("memoryId") memoryId: Int): ApiResult<Unit>
}
