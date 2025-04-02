package com.ssafy.achu.ui.memory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.memoryRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.getErrorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MemoryViewModel_안주현"

class MemoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MemoryUIState())
    val uiState: StateFlow<MemoryUIState> = _uiState.asStateFlow()


    private var currentOffset = 0
    private var hasMoreData = true




    // 메모리 리스트를 로드하는 함수
    fun getMemoryList(babyId: Int) {
        resetMemoryList()
        loadMoreItems(babyId)
    }

    // 더 많은 메모리 아이템을 로드하는 함수 (페이지네이션)
    fun loadMoreItems(babyId: Int) {
        if (_uiState.value.isLoading || !hasMoreData) return // 로딩 중이거나 더 이상 데이터가 없다면 종료

        _uiState.value = _uiState.value.copy(isLoading = true) // 로딩 상태로 변경

        viewModelScope.launch {
            memoryRepository.getMemories(babyId, offset = currentOffset, limit = 5).onSuccess { result ->
                val newItems = result.data

                // 새로운 아이템이 없으면 더 이상 데이터가 없다고 판단
                if (newItems.isEmpty()) {
                    hasMoreData = false
                } else {
                    val currentList = _uiState.value.memoryList
                    _uiState.value = _uiState.value.copy(
                        memoryList = currentList + newItems, // 새로운 데이터 추가
                        isLoading = false // 로딩 상태 해제
                    )

                    // 오프셋을 업데이트하여 다음 페이지를 로드할 준비
                    currentOffset++
                }

            }.onFailure { error ->
                val errorResponse = error.getErrorResponse(retrofit)
                Log.d(TAG, "loadMoreItems error: ${errorResponse}")
                _uiState.value = _uiState.value.copy(isLoading = false) // 로딩 상태 해제
            }
        }
    }

    private fun resetMemoryList() {
        currentOffset = 0
        hasMoreData = true
        _uiState.value = MemoryUIState() // UI 상태 초기화
    }

}
