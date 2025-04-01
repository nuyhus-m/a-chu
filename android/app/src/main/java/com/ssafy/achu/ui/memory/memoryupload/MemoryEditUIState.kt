package com.ssafy.achu.ui.memory.memoryupload

import com.ssafy.achu.data.model.memory.SingleMemoryResponse
import okhttp3.MultipartBody

data class MemoryEditUIState (
    val babyId: Int = 0,
    val memoryTitle: String = "",
    val memoryContent: String = "",
    val sendIMage: List<MultipartBody.Part> = emptyList(),
    val selectedMemory : SingleMemoryResponse = SingleMemoryResponse(
        imgUrls = emptyList(),
        title = "",
        content = "",
        id = 0,
        createdAt = "",
        updatedAt = ""
    )
    , val toastString: String = "",
    val ifChangedImage : Boolean = false,
    val ifChangedMemory : Boolean = false

)