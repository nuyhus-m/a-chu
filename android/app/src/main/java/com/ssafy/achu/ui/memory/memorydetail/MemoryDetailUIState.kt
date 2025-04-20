package com.ssafy.achu.ui.memory.memorydetail

import com.ssafy.achu.data.model.memory.SingleMemoryResponse
import okhttp3.MultipartBody

data class MemoryDetailUIState (
    val isChanged: Boolean = false,
    val toastString: String = "",
    val showDeleteDialog: Boolean = false,
    val selectedMemory : SingleMemoryResponse = SingleMemoryResponse(
        imgUrls = emptyList(),
        title = "",
        content = "",
        id = 0,
        createdAt = "",
        updatedAt = ""
    )

)