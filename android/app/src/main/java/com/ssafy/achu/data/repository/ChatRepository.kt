package com.ssafy.achu.data.repository

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.chat.ChatRoomRequest
import com.ssafy.achu.data.model.chat.ChatRoomResponse
import com.ssafy.achu.data.model.chat.Message
import com.ssafy.achu.data.model.chat.MessageCountResponse
import com.ssafy.achu.data.model.chat.RoomIdResponse
import com.ssafy.achu.data.network.RetrofitUtil

class ChatRepository {

    private val chatService = RetrofitUtil.chatService

    suspend fun createChatRoom(chatRoomRequest: ChatRoomRequest): Result<ApiResult<IdResponse>> {
        return kotlin.runCatching {
            chatService.createChatRoom(chatRoomRequest)
        }
    }

    suspend fun getChatRooms(): Result<ApiResult<List<ChatRoomResponse>>> {
        return kotlin.runCatching {
            chatService.getChatRooms()
        }
    }

    suspend fun getUnreadCount(): Result<ApiResult<MessageCountResponse>> {
        return kotlin.runCatching {
            chatService.getUnreadCount()
        }
    }

    suspend fun getMessages(roomId: Int): Result<ApiResult<List<Message>>> {
        return kotlin.runCatching {
            chatService.getMessages(roomId)
        }
    }

    suspend fun checkChatRoomExistence(
        productId: Int,
        sellerId: Int
    ): Result<ApiResult<RoomIdResponse>> {
        return kotlin.runCatching {
            chatService.checkChatRoomExistence(productId, sellerId)
        }
    }
}