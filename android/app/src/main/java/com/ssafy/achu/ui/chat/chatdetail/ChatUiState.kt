package com.ssafy.achu.ui.chat.chatdetail

import com.ssafy.achu.data.model.chat.Message

data class ChatUiState(
    val inputText: String = "",
    val messages: List<Message> = emptyList(),
    val lastReadMessageId: Int = -1
)
