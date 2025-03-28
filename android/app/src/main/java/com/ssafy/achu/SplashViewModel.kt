package com.ssafy.achu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.authRepository
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.achu.core.util.Constants
import com.ssafy.achu.data.model.auth.RefreshToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "SplashViewModel"

class SplashViewModel : ViewModel() {

    sealed class LoginState {
        data object Loading : LoginState()
        data object Authenticated : LoginState()
        data object NotAuthenticated : LoginState()
    }

    // 상태를 관찰할 수 있는 StateFlow
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun checkAutoLogin() {
        viewModelScope.launch {
            val accessTokenIssueTime = sharedPreferencesUtil.getAccessTokenIssuedAt()
            val refreshTokenIssueTime = sharedPreferencesUtil.getRefreshTokenIssuedAt()
            val tokens = sharedPreferencesUtil.getTokens()

            if (tokens == null || accessTokenIssueTime == 0L || refreshTokenIssueTime == 0L) {
                _loginState.value = LoginState.NotAuthenticated
                return@launch
            }

            val refreshToken = tokens.refreshToken
            val accessTokenExpiresIn = tokens.accessTokenExpiresIn
            val refreshTokenExpiresIn = tokens.refreshTokenExpiresIn
            val refreshTokenRenewAvailableSeconds = tokens.refreshTokenRenewAvailableSeconds

            val currentTime = System.currentTimeMillis() / 1000 // 현재 시간 (초 단위)
            val accessTokenExpiryTime =
                accessTokenIssueTime + accessTokenExpiresIn // Access Token 만료 시간
            val refreshTokenExpiryTime =
                refreshTokenIssueTime + refreshTokenExpiresIn // Refresh Token 만료 시간
            val refreshTokenRenewTime =
                refreshTokenExpiryTime - refreshTokenRenewAvailableSeconds // 리프레시 토큰 갱신 가능 시간

            when {

                // ✅ Refresh Token 갱신 가능 기간이면 Refresh Token 재발급 시도
                currentTime in refreshTokenRenewTime..<refreshTokenExpiryTime -> {
                    renewTokens(RefreshToken(refreshToken))
                }

                // ✅ Access Token이 아직 유효하면 로그인 유지
                currentTime < accessTokenExpiryTime -> {
                    _loginState.value = LoginState.Authenticated
                }

                // ✅ Access Token 만료되었고 Refresh Token이 유효하면 Access Token 갱신
                currentTime in accessTokenExpiryTime..<refreshTokenExpiryTime -> {
                    refreshAccessToken(RefreshToken(refreshToken))
                }

                // ❌ Refresh Token도 만료됨 → 로그인 필요
                else -> {
                    _loginState.value = LoginState.NotAuthenticated
                }

            }
        }
    }

    private suspend fun refreshAccessToken(refreshToken: RefreshToken) {
        authRepository.refreshAccessToken(refreshToken)
            .onSuccess { response ->
                Log.d(TAG, "refreshAccessToken: $response")
                if (response.result == Constants.SUCCESS) {
                    val issuedAt = System.currentTimeMillis() / 1000
                    sharedPreferencesUtil.saveAccessTokenIssuedAt(issuedAt)
                    sharedPreferencesUtil.updateAccessToken(response.data)

                    _loginState.value = LoginState.Authenticated
                } else {
                    _loginState.value = LoginState.NotAuthenticated
                }
            }.onFailure {
                Log.d(TAG, "refreshAccessToken error: ${it.message}")
                _loginState.value = LoginState.NotAuthenticated
            }
    }

    private suspend fun renewTokens(refreshToken: RefreshToken) {
        authRepository.renewTokens(refreshToken)
            .onSuccess { response ->
                Log.d(TAG, "renewTokens: $response")
                if (response.result == Constants.SUCCESS) {
                    val issuedAt = System.currentTimeMillis() / 1000
                    sharedPreferencesUtil.saveAccessTokenIssuedAt(issuedAt)
                    sharedPreferencesUtil.saveRefreshTokenIssuedAt(issuedAt)
                    sharedPreferencesUtil.saveTokens(response.data)
                    _loginState.value = LoginState.Authenticated
                } else {
                    _loginState.value = LoginState.NotAuthenticated
                }
            }.onFailure {
                Log.d(TAG, "renewTokens error: ${it.message}")
                _loginState.value = LoginState.NotAuthenticated
            }
    }
}