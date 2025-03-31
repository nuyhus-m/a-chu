package com.ssafy.achu.ui.memory.memorydetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.memory.MemoryRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "MemoryDetailViewModel 안주현"
class MemoryDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MemoryDetailUIState())
    val uiState: StateFlow<MemoryDetailUIState> = _uiState.asStateFlow()

    private val _isChanged = MutableSharedFlow<Boolean>()
    val isChanged: SharedFlow<Boolean> = _isChanged

    fun showDeleteDialog(boolean: Boolean) {
        _uiState.value = _uiState.value.copy(showDeleteDialog = boolean)
    }

    fun updateToastString(toastString: String) {
        _uiState.value = _uiState.value.copy(toastString = toastString)
    }

    fun getMemory(memoryId: Int) {
        viewModelScope.launch {
            ApplicationClass.Companion.memoryRepository.getMemory(memoryId).onSuccess {
                _uiState.value = _uiState.value.copy(
                    selectedMemory = it.data
                )
                Log.d(TAG, "getMemory: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "getMemory: ${errorResponse}")

            }
        }

    }


    fun deleteMemory() {
        viewModelScope.launch {
            ApplicationClass.Companion.memoryRepository.deleteMemory(uiState.value.selectedMemory.id).onSuccess {
                Log.d(TAG, "deleteMemory: ${it}")
                updateToastString("추억 삭제 완료")
                _isChanged.emit(true)
            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "deleteMemory: ${errorResponse}")
                updateToastString("추억 삭제 실패")
                _isChanged.emit(false)
            }
        }
    }

}