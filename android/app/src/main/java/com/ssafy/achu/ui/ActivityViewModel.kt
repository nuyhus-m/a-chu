package com.ssafy.achu.ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.babyRepository
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.stompService
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.UploadProductRequest
import com.ssafy.achu.data.network.StompService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


private const val TAG = "ActivityViewModel_안주현"

class ActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ActivityUIState())
    val uiState: StateFlow<ActivityUIState> = _uiState.asStateFlow()

    private val _getProductSuccess = MutableSharedFlow<Boolean>()
    val getProductSuccess: SharedFlow<Boolean> = _getProductSuccess.asSharedFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    private var backgroundJobToken: Job? = null
    private val backgroundTimeoutMs = 60000L // 1분

    init {
        getUserinfo()
        // 앱이 시작될 때 STOMP 연결 시작
        connectToStompServer()
    }

    // STOMP 서버에 연결
    private fun connectToStompServer() {
        viewModelScope.launch {
            stompService.connect()
        }
    }

    fun updateSelectedBaby(baby: BabyResponse) {
        _uiState.update {
            it.copy(
                selectedBaby = baby
            )
        }
    }

    fun getUserinfo() {
        viewModelScope.launch {
            userRepository.getMyInfo()
                .onSuccess {
                    if (it.result == "SUCCESS") {
                        val userData = it.data
                        _uiState.update {
                            it.copy(
                                user = userData
                            )
                        }
                        Log.d(TAG, "getUserinfo: ${it}")
                        getBabyList()
                    }
                }.onFailure {
                    Log.d(TAG, "getUserinfo: ${it.message}")
                }
        }
    }

    val isBabyListLoading = MutableStateFlow("로딩전")
    fun getBabyList() {
        isBabyListLoading.value = "로딩중"
        viewModelScope.launch {
            babyRepository.getBabyList().onSuccess {
                _uiState.update { currentState ->
                    currentState.copy(
                        babyList = it.data,
                    )
                }
                Log.d(TAG, "getBabyList: ${it}")
                Log.d(TAG, "getBabyList: ${uiState.value.babyList}")
                if (uiState.value.babyList.isEmpty()) {
                    updateShowCreateDialog(true)
                }

            }.onFailure {
                Log.d(TAG, "getBabyList: ${it.message}")

            }
        }
    }

    fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            productRepository.getProductDetail(productId)
                .onSuccess { response ->
                    Log.d(TAG, "getProductDetail: $response")
                    if (response.result == SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                product = response.data
                            )
                        }
                        _getProductSuccess.emit(true)
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "getProductDetail errorResponse: $errorResponse")
                    Log.d(TAG, "getProductDetail error: ${it.message}")
                    _getProductSuccess.emit(false)
                    _errorMessage.emit(errorResponse.message)
                }
        }
    }

    fun saveProductDetail(productDetailResponse: ProductDetailResponse, imgUris: List<Uri>) {
        _uiState.update {
            it.copy(
                product = productDetailResponse,
                previewImgUris = imgUris
            )
        }
    }

    fun saveProductInfo(
        uploadProductRequest: UploadProductRequest,
        multiPartImages: List<MultipartBody.Part>,
        babyName: String
    ) {
        _uiState.update {
            it.copy(
                uploadProductRequest = uploadProductRequest,
                multiPartImages = multiPartImages,
                uploadBabyName = babyName
            )
        }
    }

    fun updateShowCreateDialog(boolean: Boolean) {
        _uiState.update {
            it.copy(
                showCreateDialog = boolean
            )
        }
    }

    var isBottomNavVisible = mutableStateOf(true)
        private set

    fun showBottomNav() {
        isBottomNavVisible.value = true
    }

    fun hideBottomNav() {
        isBottomNavVisible.value = false
    }

    fun onAppForeground() {
        // 백그라운드 작업 취소
        backgroundJobToken?.cancel()
        backgroundJobToken = null

        // 앱이 포그라운드로 돌아올 때 처리
        viewModelScope.launch {
            if (stompService.connectionState.value !is StompService.ConnectionState.Connected) {
                stompService.connect()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // 이미 실행 중인 백그라운드 작업 취소
        backgroundJobToken?.cancel()

        // 새 백그라운드 작업 시작 - 일정 시간 후 연결 해제
        backgroundJobToken = viewModelScope.launch {
            delay(backgroundTimeoutMs)
            stompService.cleanup()
        }
    }
}