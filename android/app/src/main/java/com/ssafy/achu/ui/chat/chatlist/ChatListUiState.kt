package com.ssafy.achu.ui.chat.chatlist

import com.ssafy.achu.data.model.chat.ChatRoomResponse

data class ChatListUiState(
    val chatRooms: List<ChatRoomResponse> = emptyList()
)