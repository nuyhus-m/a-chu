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

    fun showDeleteDialog(boolean: Boolean) {
        _uiState.value = _uiState.value.copy(showDeleteDialog = boolean)
    }


    fun memoryTitleUpdate(title:String){
        _uiState.value = _uiState.value.copy(memoryTitle = title)
    }

    fun memoryContentUpdate(content:String){
        _uiState.value = _uiState.value.copy(memoryContent = content)
    }

    fun memoryImageUpdate(images: List<MultipartBody.Part>) {
    _uiState.value = _uiState.value.copy(sendIMage = images)
    }

    fun uploadMemory() {
        viewModelScope.launch {
            memoryRepository.createMemory(
                babyId = uiState.value.babyId,
                memoryImages = uiState.value.sendIMage,
                request = MemoryRequest(
                    title = uiState.value.memoryTitle,
                    content = uiState.value.memoryContent
                )
            ).onSuccess {
                Log.d(TAG, "uploadMemory: ${it}")
                getMemory(it.data.id)

            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "uploadMemory: ${errorResponse}")
            }

        }
    }


    fun getMemory(memoryId: Int) {
        viewModelScope.launch {
            memoryRepository.getMemory(memoryId).onSuccess {
                _uiState.value = _uiState.value.copy(
                    selectedMemory = it.data
                )
                Log.d(TAG, "getMemory: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getMemory: ${errorResponse}")

            }
        }

    }

    fun getMemoryList(babyId: Int) {
        viewModelScope.launch {
            memoryRepository.getMemories(babyId).onSuccess {
                _uiState.value = _uiState.value.copy(
                    memoryList = it.data
                )
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "getMemoryList: ${errorResponse}")
            }

        }
    }

    fun changeMemory() {
        viewModelScope.launch {
            memoryRepository.updateMemory(
                memoryId = uiState.value.selectedMemory.id,
                request = MemoryRequest(
                    title = uiState.value.memoryTitle,
                    content = uiState.value.memoryContent
                )
            ).onSuccess {
                Log.d(TAG, "changeMemory: ${it}")
                getMemory(uiState.value.selectedMemory.id)
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "changeMemory: ${errorResponse}")
            }
        }

    }

    fun deleteMemory() {
        viewModelScope.launch {
            memoryRepository.deleteMemory(uiState.value.selectedMemory.id).onSuccess {
                Log.d(TAG, "deleteMemory: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "deleteMemory: ${errorResponse}")
            }
        }
    }

    fun updateImage() {
        viewModelScope.launch {
            memoryRepository.updateMemoryImages(
                memoryId = uiState.value.selectedMemory.id,
                memoryImages = uiState.value.sendIMage
            ).onSuccess {
                Log.d(TAG, "updateImage: ${it}")
                getMemory(uiState.value.selectedMemory.id)
                }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "updateImage: ${errorResponse}")
            }
        }
    }
}
