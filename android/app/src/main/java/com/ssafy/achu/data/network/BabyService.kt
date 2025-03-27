package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.baby.BabyBirthRequest
import com.ssafy.achu.data.model.baby.BabyGenderRequest
import com.ssafy.achu.data.model.baby.BabyNicknameRequest
import com.ssafy.achu.data.model.baby.BabyRequest
import com.ssafy.achu.data.model.baby.BabyResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface BabyService {

    // 자녀 등록
    @Multipart
    @POST("/babies")
    suspend fun registerBaby(
        @Part profileImage: MultipartBody.Part,
        @Part("request") request: BabyRequest
    ): ApiResult<IdResponse>

    // 특정 자녀 조회
    @GET("/babies/{id}")
    suspend fun getBaby(@Path("id") babyId: Int): ApiResult<BabyResponse>

    // 자녀 목록 조회(유저아이디 안받고 어떻게 불러오는거지..?)
    @GET("/babies")
    suspend fun getBabyList(): ApiResult<List<BabyResponse>>

    // 자녀 프로필 사진 변경
    @Multipart
    @PATCH("/babies/{id}/profile-image")
    suspend fun updateProfileImage(
        @Path("id") babyId: Int,
        @Part profileImage: MultipartBody.Part
    ): ApiResult<Unit>

    // 자녀 닉네임 변경
    @PATCH("/babies/{id}/nickname")
    suspend fun updateNickname(
        @Path("id") babyId: Int,
        @Body request: BabyNicknameRequest
    ): ApiResult<Unit>

    // 자녀 생년월일 변경
    @PATCH("/babies/{id}/birth")
    suspend fun updateBirth(
        @Path("id") babyId: Int,
        @Body request: BabyBirthRequest
    ): ApiResult<Unit>

    // 자녀 성별 변경
    @PATCH("/babies/{id}/gender")
    suspend fun updateGender(
        @Path("id") babyId: Int,
        @Body request: BabyGenderRequest
    ): ApiResult<Unit>

    // 자녀 삭제
    @DELETE("/babies/{id}")
    suspend fun deleteBaby(@Path("id") babyId: Int): ApiResult<IdResponse>
}