package com.ssafy.achu.ui.memory

import com.ssafy.achu.data.model.memory.MemoryResponse
import com.ssafy.achu.data.model.memory.SingleMemoryResponse
import okhttp3.MultipartBody

data class MemoryUIState (
    val babyId: Int = 0,
    val memoryList: List<MemoryResponse> = emptyList(),
    val isLoading: Boolean = false
)