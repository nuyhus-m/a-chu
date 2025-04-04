package com.ssafy.achu.ui

import android.net.Uri
import android.util.Log
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

    init {
        getUserinfo()
        stompConnect()
    }

    private fun stompConnect() {
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


    fun getBabyList() {
        viewModelScope.launch {
            babyRepository.getBabyList().onSuccess {
                _uiState.update { currentState ->
                    currentState.copy(
                        babyList = it.data,
                    )
                }
                Log.d(TAG, "getBabyList: ${it}")
                Log.d(TAG, "getBabyList: ${uiState.value.babyList}")
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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            stompService.disconnect()
        }
    }
}