package com.ssafy.achu.ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.babyRepository
import com.ssafy.achu.core.ApplicationClass.Companion.chatRepository
import com.ssafy.achu.core.ApplicationClass.Companion.fcmRepository
import com.ssafy.achu.core.ApplicationClass.Companion.json
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.stompService
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.Token
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.ProductResponse
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

    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount.asStateFlow()

    private val _getProductSuccess = MutableSharedFlow<Boolean>()
    val getProductSuccess: SharedFlow<Boolean> = _getProductSuccess.asSharedFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    private val _categoryList = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categoryList: StateFlow<List<CategoryResponse>> = _categoryList

    private val _recommendItemList = MutableStateFlow<List<ProductResponse>>(emptyList())
    val recommendItemList: StateFlow<List<ProductResponse>> = _recommendItemList

    init {
        getUserinfo()
        getCategoryList()
        viewModelScope.launch {
            stompService.connect()
        }
        getUnreadCount()
    }

    fun getCategoryList() {
        viewModelScope.launch {
            productRepository.getCategoryList().onSuccess { result ->
                Log.d(TAG, "getCategoryList: ${result}")
                _categoryList.value = result.data

            }.onFailure { error ->
                val errorResponse = error.getErrorResponse(retrofit)
                Log.d(TAG, "getCategoryList: ${errorResponse}")
            }
        }
    }

    fun updateSelectedBaby(baby: BabyResponse) {
        _uiState.update {
            it.copy(
                selectedBaby = baby
            )
        }
        getRecommendItemList(baby.id)
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
            productRepository.getProductDetail(productId, uiState.value.selectedBaby!!.id)
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
                    Log.d(TAG, "getProductDetail: ${uiState.value.selectedBaby!!.id}")

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

    // 앱이 포그라운드로 돌아올 때 처리
    private fun getUnreadCount() {
        viewModelScope.launch {
            chatRepository.getUnreadCount()
                .onSuccess { response ->
                    Log.d(TAG, "getUnreadCount: $response")
                    if (response.result == SUCCESS) {
                        _unreadCount.value = response.data.unreadMessageCount
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "getUnreadCount errorResponse: $errorResponse")
                    Log.d(TAG, "getUnreadCount error: ${it.message}")
                }
        }
    }

    fun subscribeToNewMessage() {
        if (uiState.value.user == null) return
        viewModelScope.launch {
            stompService.subscribeToDestination("/read/chat/users/${uiState.value.user!!.id}/message-arrived")
                .onSuccess { response ->
                    Log.d(TAG, "subscribeToNewMessage: success")
                    response.let { body ->
                        try {
                            body.collect {
                                val data = json.decodeFromString<String>(it.bodyAsText)
                                Log.d(TAG, "subscribeToNewMessage: $data")
                                if (data == "NEW_MESSAGE_ARRIVED") {
                                    _unreadCount.value++
                                }
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "subscribeToNewMessage: ${e.message}")
                            stompService.connect()
                        }
                    }
                }.onFailure {
                    Log.d(TAG, "subscribeToNewMessage: ${it.message}")
                }
        }
    }

    fun updateFcmToken(token: String) {
        viewModelScope.launch {
            fcmRepository.updateToken(Token(token)).onSuccess {
                Log.d(TAG, "updateFcmToken: ${it}, 등록성공")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "updateFcmToken: ${it}")
            }
        }

    }


    fun deleteFcmToken() {
        viewModelScope.launch {
            fcmRepository.deleteToken().onSuccess {
                Log.d(TAG, "deleteFcmToken: ${it}, 삭제성공")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "deleteFcmToken: ${it}")
            }
        }

    }

    fun getRecommendItemList(babyId: Int) {
        viewModelScope.launch {
            productRepository.getRecommendedItems(babyId).onSuccess {
                _recommendItemList.value = it.data
                Log.d(TAG, "getRecommendItemList: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getRecommendItemList: ${errorResponse}")
            }

        }

    }


}