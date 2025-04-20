package com.ssafy.achu.ui.auth.signup

data class SignUpUIState(
    val id: String = "",
    val pwd: String = "",
    val pwdCheck: String = "",
    val nickname: String = "",
    val phoneNumber: String = "",

    val idMessage: String = "",
    val pwdMessage: String = "",
    val pwdCheckMessage: String = "",
    val nicknameMessage: String = "",
    val phoneNumberMessage: String = "",

    val idState: Boolean = false,
    val pwdState: Boolean = false,
    val nicknameState: Boolean = false,
    val phoneNumberState: Boolean = false,

    val idButtonState: Boolean = false,
    val nicknameButtonState: Boolean = false,
    val phoneNumberButtonState: Boolean = false,
    val buttonState: Boolean = false,

    val showDialog: Boolean = false,
    val authCode: String = "",

    val signUpSuccess: Boolean = false
)
