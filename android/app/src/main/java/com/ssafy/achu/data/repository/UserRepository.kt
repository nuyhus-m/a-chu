package com.ssafy.achu.data.repository

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
import com.ssafy.achu.data.network.RetrofitUtil
import okhttp3.MultipartBody

class UserRepository {

    private val userService = RetrofitUtil.userService

    suspend fun sendPhoneAuthRequest(phoneAuthRequest: PhoneAuthRequest): Result<ApiResult<PhoneAuthResponse>> {
        return kotlin.runCatching {
            userService.sendPhoneAuthRequest(phoneAuthRequest)
        }
    }

    suspend fun checkPhoneAuth(checkPhoneAuthRequest: CheckPhoneAuthRequest): Result<ApiResult<Unit>> {
        return kotlin.runCatching {
            userService.checkPhoneAuth(checkPhoneAuthRequest)
        }
    }

    suspend fun signUp(signUpRequest: SignUpRequest): Result<ApiResult<IdResponse>> {
        return kotlin.runCatching {
            userService.signUp(signUpRequest)
        }
    }

    suspend fun getUserInfo(userId: String): Result<ApiResult<UserInfoResponse>> {
        return kotlin.runCatching {
            userService.getUserInfo(userId)
        }
    }

    suspend fun getMyInfo(): Result<ApiResult<UserInfoResponse>> {
        return kotlin.runCatching {
            userService.getMyInfo()
        }
    }

    suspend fun checkIdUnique(username: String): Result<ApiResult<UniqueCheckResponse>> {
        return kotlin.runCatching {
            userService.checkIdUnique(username)
        }
    }

    suspend fun checkNicknameUnique(nickname: String): Result<ApiResult<UniqueCheckResponse>> {
        return kotlin.runCatching {
            userService.checkNicknameUnique(nickname)
        }
    }

    suspend fun changeNickname(nickname: NickNameRequest): Result<ApiResult<Unit>> {
        return kotlin.runCatching {
            userService.changeNickname(nickname)
        }
    }

    suspend fun uploadProfileImage(profileImage: MultipartBody.Part): Result<ApiResult<Unit>> {
        return kotlin.runCatching {
            userService.uploadProfileImage(profileImage)
        }
    }

    suspend fun changePhoneNumber(changePhoneNumberRequest: ChangePhoneNumberRequest): Result<ApiResult<Unit>> {
        return kotlin.runCatching {
            userService.changePhoneNumber(changePhoneNumberRequest)
        }
    }

    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Result<ApiResult<Unit>> {
        return kotlin.runCatching {
            userService.changePassword(changePasswordRequest)
        }
    }
}