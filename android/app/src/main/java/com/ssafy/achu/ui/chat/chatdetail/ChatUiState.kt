package com.ssafy.achu.ui.chat.chatdetail

import com.ssafy.achu.data.model.chat.Goods
import com.ssafy.achu.data.model.chat.Message
import com.ssafy.achu.data.model.chat.Partner

data class ChatUiState(
    val inputText: String = "",
    val messages: List<Message> = emptyList(),
    val lastReadMessageId: Int = -1,
    val goods: Goods? = null,
    val partner: Partner? = Partner(
        id = 0,
        nickname = "",
        profileImageUrl = " "
    ),
    val isSeller: Boolean = false,
    val isSold: Boolean = false,
    val isShowSoldDialog: Boolean = false,
    val hasChatRoom: Boolean = true,
    val isFirst: Boolean = true,
    val buttonState: Boolean = true
)
