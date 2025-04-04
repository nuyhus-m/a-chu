package com.ssafy.achu.ui.memory.memoryupload

import android.provider.SyncStateContract.Helpers.update
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

    fun isMemoryChanged(boolean: Boolean) {
        _uiState.value = _uiState.value.copy(ifChangedMemory = boolean)
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
        _uiState.value = _uiState.value.copy(loading = true)
        viewModelScope.launch {
            Log.d(TAG, "uploadMemory: ${uiState.value.sendIMage}")
            ApplicationClass.Companion.memoryRepository.createMemory(
                babyId = uiState.value.babyId,
                memoryImages = uiState.value.sendIMage,
                request = MemoryRequest(
                    title = uiState.value.memoryTitle,
                    content = uiState.value.memoryContent
                )

            ).onSuccess {
                updateToastString("추억 작성 완료!")
                getMemory(it.data.id)


            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "uploadMemory: ${errorResponse}")
                _uiState.value = _uiState.value.copy(loading = false)
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
                if(uiState.value.ifChangedMemory|| uiState.value.ifChangedImage){
                    _isChanged.emit(true)
                }

            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "getMemory: ${errorResponse}")

            }
        }

    }


    fun changeMemory() {
        _uiState.value = _uiState.value.copy(loading = true)
        viewModelScope.launch {
            ApplicationClass.Companion.memoryRepository.updateMemory(
                memoryId = uiState.value.selectedMemory.id,
                request = MemoryRequest(
                    title = uiState.value.memoryTitle,
                    content = uiState.value.memoryContent
                )
            ).onSuccess {
                isMemoryChanged(true)
                if (uiState.value.ifChangedImage) {
                    updateImage()
                }else{
                    Log.d(TAG, "changeMemory: ${it}")

                    getMemory(uiState.value.selectedMemory.id)
                    updateToastString("추억 수정 완료!")

                }


            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "changeMemory: ${errorResponse}")
                _uiState.value = _uiState.value.copy(loading = false)
            }
        }

    }


    fun updateImage() {
        _uiState.value = _uiState.value.copy(loading = true)
        viewModelScope.launch {
            Log.d(TAG, "updateImage: 이미지 보내는 함수 ${uiState.value.sendIMage}")
            ApplicationClass.Companion.memoryRepository.updateMemoryImages(
                memoryId = uiState.value.selectedMemory.id,
                memoryImages = uiState.value.sendIMage
            ).onSuccess {
                Log.d(TAG, "updateImage: ${it}")
                getMemory(uiState.value.selectedMemory.id)
                updateToastString("추억 수정 완료!")
                _isChanged.emit(true)
            }.onFailure {
                val errorResponse = it.getErrorResponse(ApplicationClass.Companion.retrofit)
                Log.d(TAG, "updateImage: ${errorResponse}")
                updateToastString("이미지 업로드 실패!")
                _isChanged.emit(false)
                _uiState.value = _uiState.value.copy(loading = false)

            }
        }
    }


}