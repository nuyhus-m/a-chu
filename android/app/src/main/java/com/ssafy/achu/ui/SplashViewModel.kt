package com.ssafy.achu.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.authRepository
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.achu.core.util.Constants
import com.ssafy.achu.data.model.auth.RefreshToken
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

private const val TAG = "SplashViewModel"

class SplashViewModel : ViewModel() {

    private val _isAutoLogin = MutableSharedFlow<Boolean>()
    val isAutoLogin: SharedFlow<Boolean> = _isAutoLogin.asSharedFlow()

    fun checkAutoLogin() {
        viewModelScope.launch {
            val accessTokenIssueTime = sharedPreferencesUtil.getAccessTokenIssuedAt()
            val refreshTokenIssueTime = sharedPreferencesUtil.getRefreshTokenIssuedAt()
            val tokens = sharedPreferencesUtil.getTokens()

            if (tokens == null || accessTokenIssueTime == 0L || refreshTokenIssueTime == 0L) {
                _isAutoLogin.emit(false)
                return@launch
            }

            val accessToken = tokens.accessToken
            val refreshToken = tokens.refreshToken
            val accessTokenExpiresIn = tokens.accessTokenExpiresIn
            val refreshTokenExpiresIn = tokens.refreshTokenExpiresIn
            val refreshTokenRenewAvailableSeconds = tokens.refreshTokenRenewAvailableSeconds

            Log.d(TAG, "checkAutoLogin accessToken: $accessToken")
            Log.d(TAG, "checkAutoLogin refreshToken: $refreshToken")

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
                    Log.d(TAG, "checkAutoLogin: 1")
                    renewTokens(RefreshToken("Bearer $refreshToken"))
                }

                // ✅ Access Token이 아직 유효하면 로그인 유지
                currentTime < accessTokenExpiryTime -> {
                    Log.d(TAG, "checkAutoLogin: 2")
                    _isAutoLogin.emit(true)
                }

                // ✅ Access Token 만료되었고 Refresh Token이 유효하면 Access Token 갱신
                currentTime in accessTokenExpiryTime..<refreshTokenExpiryTime -> {
                    Log.d(TAG, "checkAutoLogin: 3")
                    refreshAccessToken(RefreshToken("Bearer $refreshToken"))
                }

                // ❌ Refresh Token도 만료됨 → 로그인 필요
                else -> {
                    Log.d(TAG, "checkAutoLogin: 4")
                    _isAutoLogin.emit(false)
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

                    _isAutoLogin.emit(true)
                } else {
                    _isAutoLogin.emit(false)
                }
            }.onFailure {
                Log.d(TAG, "refreshAccessToken error: ${it.message}")
                _isAutoLogin.emit(false)
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
                    _isAutoLogin.emit(true)
                } else {
                    _isAutoLogin.emit(false)
                }
            }.onFailure {
                Log.d(TAG, "renewTokens error: ${it.message}")
                _isAutoLogin.emit(false)
            }
    }
}