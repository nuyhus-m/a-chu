package com.ssafy.achu.data.repository

import android.util.Log
import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.memory.MemoryRequest
import com.ssafy.achu.data.model.memory.MemoryResponse
import com.ssafy.achu.data.model.memory.SingleMemoryResponse
import com.ssafy.achu.data.network.RetrofitUtil
import okhttp3.MultipartBody

private const val TAG = "MemoryRepository 안주현"
class MemoryRepository {

    private val memoryService = RetrofitUtil.memoryService

    //  추억 생성
    suspend fun createMemory(
        babyId: Int,
        images: List<MultipartBody.Part>,
        request: MemoryRequest
    ): Result<ApiResult<IdResponse>> {
        Log.d(TAG, "createMemory: ${images}")
        return runCatching {
            memoryService.createMemory(babyId, images, request)
        }
    }

    //  특정 추억 조회
    suspend fun getMemory(memoryId: Int): Result<ApiResult<SingleMemoryResponse>> {
        return runCatching {
            memoryService.getMemory(memoryId)
        }
    }

    //  특정 자녀의 추억 목록 조회
    suspend fun getMemories(
        babyId: Int,
        offset: Int = 0,
        limit: Int = 20,
        sort: String = "LATEST"
    ): Result<ApiResult<List<MemoryResponse>>> {
        return runCatching {
            memoryService.getMemories(babyId, offset, limit, sort)
        }
    }

    //  추억 사진 변경
    suspend fun updateMemoryImages(
        memoryId: Int,
        memoryImages: List<MultipartBody.Part>
    ): Result<ApiResult<Unit>> {
        return runCatching {
            memoryService.updateMemoryImages(memoryId, memoryImages)
        }
    }

    //  추억 정보 변경
    suspend fun updateMemory(
        memoryId: Int,
        request: MemoryRequest
    ): Result<ApiResult<Unit>> {
        return runCatching {
            memoryService.updateMemory(memoryId, request)
        }
    }

    // 추억 삭제
    suspend fun deleteMemory(memoryId: Int): Result<ApiResult<Unit>> {
        return runCatching {
            memoryService.deleteMemory(memoryId)
        }
    }
}
