package com.ssafy.achu.ui.mypage.userinfo

import com.ssafy.achu.data.model.auth.UserInfoResponse

data class UserInfoUIState(
    val oldPassword: String = "",
    val newPassword: String = "",
    val newPasswordCheck: String = "",
    val isPasswordMatch: Boolean = true,
    val isCorrectPWD: Boolean = true,
    val isCorrectOldPWD: Boolean = true,

    val isPhoneNumberValid: Boolean = true,
    val phoneNumber: String = "",


    val newNickname: String = "",
    val isUniqueNickname: String = "",
    val isCorrectNickname: Boolean = true,


    val showNickNameUpdateDialog: Boolean = false,
    val showPasswordUpdateDialog: Boolean = false,
    val logoutDialog: Boolean = false,
    val deleteUserDialog: Boolean = false,

    )