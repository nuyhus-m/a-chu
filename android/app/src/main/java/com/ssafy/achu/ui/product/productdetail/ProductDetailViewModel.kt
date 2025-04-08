package com.ssafy.achu.ui.product.productdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
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

private const val TAG = "ProductDetailViewModel"

class ProductDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    private val _isLikeSuccess = MutableSharedFlow<Boolean>()
    val isLikeSuccess: SharedFlow<Boolean> = _isLikeSuccess.asSharedFlow()

    private val _isUnLikeSuccess = MutableSharedFlow<Boolean>()
    val isUnLikeSuccess: SharedFlow<Boolean> = _isUnLikeSuccess.asSharedFlow()

    private val _navigateEvents = MutableSharedFlow<Boolean>()
    val navigateEvents: SharedFlow<Boolean> = _navigateEvents.asSharedFlow()

    fun updateShowDeleteDialog(dialogState: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showDeleteDialog = dialogState
            )
        }
    }

    fun updateShowUploadDialog(dialogState: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showUploadDialog = dialogState
            )
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            productRepository.deleteProduct(productId)
                .onSuccess { response ->
                    Log.d(TAG, "deleteProduct: $response")
                    if (response.result == SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                isDeleteSuccess = true
                            )
                        }
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "deleteProduct errorResponse: $errorResponse")
                    Log.d(TAG, "deleteProduct error: ${it.message}")
                    updateShowDeleteDialog(false)
                    _toastMessage.emit(errorResponse.message)
                }
        }
    }

    fun likeProduct(productId: Int, babyId: Int) {
        viewModelScope.launch {
            productRepository.likeProduct(productId, babyId)
                .onSuccess { response ->
                    Log.d(TAG, "likeProduct: $response")
                    if (response.result == SUCCESS) {
                        _isLikeSuccess.emit(true)
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "likeProduct errorResponse: $errorResponse")
                    Log.d(TAG, "likeProduct error: ${it.message}")
                    _toastMessage.emit(errorResponse.message)
                }
        }
    }

    fun unlikeProduct(productId: Int) {
        viewModelScope.launch {
            productRepository.unlikeProduct(productId)
                .onSuccess { response ->
                    Log.d(TAG, "unlikeProduct: $response")
                    if (response.result == SUCCESS) {
                        _isUnLikeSuccess.emit(true)
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "unlikeProduct errorResponse: $errorResponse")
                    Log.d(TAG, "unlikeProduct error: ${it.message}")
                    _toastMessage.emit(errorResponse.message)
                }
        }
    }

    fun uploadProduct(
        uploadProductRequest: UploadProductRequest,
        images: List<MultipartBody.Part>,
        isWithMemory: Boolean
    ) {
        viewModelScope.launch {
            productRepository.uploadProduct(
                images = images,
                request = uploadProductRequest,
            ).onSuccess { response ->
                Log.d(TAG, "uploadProduct: $response")
                if (response.result == SUCCESS) {
                    _navigateEvents.emit(isWithMemory)
                    updateShowUploadDialog(false)
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "uploadProduct errorResponse: $errorResponse")
                Log.d(TAG, "uploadProduct error: ${it.message}")
                _toastMessage.emit(errorResponse.message)
                updateShowUploadDialog(false)
            }
        }
    }
}

