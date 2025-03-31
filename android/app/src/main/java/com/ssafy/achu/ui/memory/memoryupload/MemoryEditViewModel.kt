package com.ssafy.achu.ui.memory.memoryupload

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

private const val TAG = "MemoryEditViewModel 안주현"

class MemoryEditViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MemoryEditUIState())
    val uiState: StateFlow<MemoryEditUIState> = _uiState.asStateFlow()

    private val _isChanged = MutableSharedFlow<Boolean>()
    val isChanged: SharedFlow<Boolean> = _isChanged

    fun isImageChanged(boolean: Boolean) {
        _uiState.value = _uiState.value.copy(ifChangedImage = boolean)
    }


    fun memoryTitleUpdate(title: String) {
        Log.d(TAG, "memoryTitleUpdate: ${uiState.value.memoryTitle}")
        _uiState.value = _uiState.value.copy(memoryTitle = title)
        Log.d(TAG, "memoryTitleUpdate: ${uiState.value.memoryTitle}")

    }

    fun memoryContentUpdate(content: String) {
        _uiState.value = _uiState.value.copy(memoryContent = content)
    }

    fun memoryImageUpdate(images: List<MultipartBody.Part>) {
        _uiState.value = _uiState.value.copy(sendIMage = images)
    }


    fun babyIdUpdate(babyId: Int) {
        _uiState.value = _uiState.value.copy(babyId = babyId)
    }

    fun updateToastString(toastString: String) {
        _uiState.value = _uiState.value.copy(toastString = toastString)
    }

    fun uploadMemory() {
        viewModelScope.launch {
            ApplicationClass.Companion.memoryRepository.createMemory(
                babyId = uiState.value.babyId,
                memoryImages = uiState.value.sendIMage,
                request = MemoryRequest(
                    title = uiState.value.memoryTitle,
                    content = uiState.value.memoryContent
                )
            ).onSuccess {
                Log.d(TAG, "uploadMemory: ${it}")
                updateToastString("추억 작성 완료!")
                getMemory(it.data.id)
                _isChanged.emit(true)

            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "uploadMemory: ${errorResponse}")
            }

        }
    }


    fun getMemory(memoryId: Int) {
        viewModelScope.launch {
            ApplicationClass.Companion.memoryRepository.getMemory(memoryId).onSuccess {
                _uiState.value = _uiState.value.copy(
                    selectedMemory = it.data,
                    memoryTitle = it.data.title,
                    memoryContent = it.data.content
                )
                Log.d(TAG, "getMemory: ${it}")
            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "getMemory: ${errorResponse}")

            }
        }

    }


    fun changeMemory() {
        viewModelScope.launch {
            ApplicationClass.Companion.memoryRepository.updateMemory(
                memoryId = uiState.value.selectedMemory.id,
                request = MemoryRequest(
                    title = uiState.value.memoryTitle,
                    content = uiState.value.memoryContent
                )
            ).onSuccess {
                Log.d(TAG, "changeMemory: ${it}")

                getMemory(uiState.value.selectedMemory.id)
                updateToastString("추억 수정 완료!")
                _isChanged.emit(true)

            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "changeMemory: ${errorResponse}")
            }
        }

    }


    fun updateImage() {
        viewModelScope.launch {
            ApplicationClass.Companion.memoryRepository.updateMemoryImages(
                memoryId = uiState.value.selectedMemory.id,
                memoryImages = uiState.value.sendIMage
            ).onSuccess {
                Log.d(TAG, "updateImage: ${it}")
//                getMemory(uiState.value.selectedMemory.id)
//                updateToastString("추억 수정 완료!")
//                _isChanged.emit(true)
            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "updateImage: ${errorResponse}")
            }
        }
    }


}