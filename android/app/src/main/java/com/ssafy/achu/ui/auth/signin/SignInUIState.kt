package com.ssafy.achu.ui.auth.signin

data class SignInUIState(
    val id: String = "",
    val pwd: String = "",
    val buttonState: Boolean = false,
    val signInSuccess: Boolean = false
)