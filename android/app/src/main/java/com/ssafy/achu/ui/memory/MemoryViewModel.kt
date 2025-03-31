package com.ssafy.achu.ui.memory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.memoryRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.memory.MemoryRequest
import com.ssafy.achu.data.model.memory.SingleMemoryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "MemoryViewModel_안주현"

class MemoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MemoryUIState())
    val uiState: StateFlow<MemoryUIState> = _uiState.asStateFlow()


    fun getMemoryList(babyId: Int) {
        viewModelScope.launch {
            memoryRepository.getMemories(babyId).onSuccess {
                _uiState.value = _uiState.value.copy(
                    memoryList = it.data
                )

                Log.d(TAG, "getMemoryList: ${it.data}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getMemoryList: ${errorResponse}")
            }

        }
    }


}
