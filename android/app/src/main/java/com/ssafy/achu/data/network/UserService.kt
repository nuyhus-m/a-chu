package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.auth.ChangePasswordRequest
import com.ssafy.achu.data.model.auth.ChangePhoneNumberRequest
import com.ssafy.achu.data.model.auth.CheckPhoneAuthRequest
import com.ssafy.achu.data.model.auth.NickNameRequest
import com.ssafy.achu.data.model.auth.PhoneAuthRequest
import com.ssafy.achu.data.model.auth.PhoneAuthResponse
import com.ssafy.achu.data.model.auth.SignUpRequest
import com.ssafy.achu.data.model.auth.UniqueCheckResponse
import com.ssafy.achu.data.model.auth.UserInfoResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @POST("/verification/request")
    suspend fun sendPhoneAuthRequest(@Body phoneAuthRequest: PhoneAuthRequest): ApiResult<PhoneAuthResponse>

    @POST("/verification/verify")
    suspend fun checkPhoneAuth(@Body checkPhoneAuthRequest: CheckPhoneAuthRequest): ApiResult<Unit>

    @POST("/users")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): ApiResult<IdResponse>

    @GET("/users/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): ApiResult<UserInfoResponse>

    @GET("/users/me")
    suspend fun getMyInfo(): ApiResult<UserInfoResponse>

    @GET("/users/username/is-unique")
    suspend fun checkIdUnique(@Query("username") username: String): ApiResult<UniqueCheckResponse>

    @GET("/users/nickname/is-unique")
    suspend fun checkNicknameUnique(@Query("nickname") nickname: String): ApiResult<UniqueCheckResponse>

    @PATCH("/users/nickname")
    suspend fun changeNickname(@Body nickname: NickNameRequest): ApiResult<Unit>

    @Multipart
    @PATCH("/users/profile-image")
    suspend fun uploadProfileImage(@Part profileImage: MultipartBody.Part): ApiResult<Unit>

    @PATCH("/users/phone")
    suspend fun changePhoneNumber(@Body changePhoneNumberRequest: ChangePhoneNumberRequest): ApiResult<Unit>

    @PATCH("/users/password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): ApiResult<Unit>

}