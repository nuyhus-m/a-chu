package com.ssafy.achu.data.repository

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.baby.BabyBirthRequest
import com.ssafy.achu.data.model.baby.BabyGenderRequest
import com.ssafy.achu.data.model.baby.BabyNicknameRequest
import com.ssafy.achu.data.model.baby.BabyRequest
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.network.RetrofitUtil
import okhttp3.MultipartBody

class BabyRepository {

    private val babyService = RetrofitUtil.babyService


    // 자녀 등록
    suspend fun registerBaby(profileImage: MultipartBody.Part, request: BabyRequest): Result<ApiResult<IdResponse>> {
        return runCatching {
            babyService.registerBaby(profileImage, request)
        }
    }

    // 특정 자녀 조회
    suspend fun getBaby(babyId: Int): Result<ApiResult<BabyResponse>> {
        return runCatching {
            babyService.getBaby(babyId)
        }
    }

    // 자녀 목록 조회(유저아이디 받아야겠지..?)
    suspend fun getBabyList(): Result<ApiResult<List<BabyResponse>>> {
        return runCatching {
            babyService.getBabyList()
        }
    }

    // 자녀 프로필 사진 변경
    suspend fun updateProfileImage(babyId: Int, profileImage: MultipartBody.Part): Result<ApiResult<Unit>> {
        return runCatching {
            babyService.updateProfileImage(babyId, profileImage)
        }
    }

    // 자녀 닉네임 변경
    suspend fun updateNickname(babyId: Int, request: BabyNicknameRequest): Result<ApiResult<Unit>> {
        return runCatching {
            babyService.updateNickname(babyId, request)
        }
    }

    // 자녀 생년월일 변경
    suspend fun updateBirth(babyId: Int, request: BabyBirthRequest): Result<ApiResult<Unit>> {
        return runCatching {
            babyService.updateBirth(babyId, request)
        }
    }

    // 자녀 성별 변경
    suspend fun updateGender(babyId: Int, request: BabyGenderRequest): Result<ApiResult<Unit>> {
        return runCatching {
            babyService.updateGender(babyId, request)
        }
    }

    // 자녀 삭제
    suspend fun deleteBaby(babyId: Int): Result<ApiResult<IdResponse>> {
        return runCatching {
            babyService.deleteBaby(babyId)
        }
    }
}