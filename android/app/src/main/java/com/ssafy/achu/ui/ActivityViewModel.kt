package com.ssafy.achu.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.babyRepository
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.baby.BabyResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


private const val TAG = "ActivityViewModel_안주현"

class ActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ActivityUIState())
    val uiState: StateFlow<ActivityUIState> = _uiState.asStateFlow()

    private val _getProductSuccess = MutableSharedFlow<Boolean>()
    val getProductSuccess: SharedFlow<Boolean> = _getProductSuccess.asSharedFlow()

    init {
        getUserinfo()
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
                }
        }
    }
}