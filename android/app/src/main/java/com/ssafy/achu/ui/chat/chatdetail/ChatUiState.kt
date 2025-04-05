package com.ssafy.achu.ui.chat.chatdetail

import com.ssafy.achu.data.model.chat.Message
import com.ssafy.achu.data.model.chat.Partner
import com.ssafy.achu.data.model.product.ProductDetailResponse

data class ChatUiState(
    val inputText: String = "",
    val messages: List<Message> = emptyList(),
    val lastReadMessageId: Int = -1,
    val product: ProductDetailResponse? = null,
    val partner: Partner? = null,
    val isShowSoldDialog: Boolean = false,
    val hasChatRoom: Boolean = true,
    val isFirst: Boolean = true
)
