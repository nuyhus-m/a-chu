package com.ssafy.achu.ui.memory

import com.ssafy.achu.data.model.memory.MemoryResponse
import com.ssafy.achu.data.model.memory.SingleMemoryResponse
import okhttp3.MultipartBody

data class MemoryUIState (
    val babyId: Int = 0,
    val memoryTitle: String = "",
    val memoryContent: String = "",
    val sendIMage: List<MultipartBody.Part> = emptyList(),
    val showDeleteDialog: Boolean = false,

    val memoryList: List<MemoryResponse> = emptyList(),
    val selectedMemory : SingleMemoryResponse = SingleMemoryResponse(
        imgUrls = emptyList(),
        title = "",
        content = "",
        id = 0,
        createdAt = "",
        updatedAt = ""
    )

)