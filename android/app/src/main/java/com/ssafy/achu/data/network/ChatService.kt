package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.chat.ChatRoomRequest
import com.ssafy.achu.data.model.chat.ChatRoomResponse
import com.ssafy.achu.data.model.chat.MessageCountResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatService {

    @POST("/chat/rooms")
    suspend fun createChatRoom(
        @Body chatRoomRequest: ChatRoomRequest
    ): ApiResult<ChatRoomResponse>

    @GET("/chat/rooms")
    suspend fun getChatRooms(): ApiResult<List<ChatRoomResponse>>

    @GET("/chat/unread-count")
    suspend fun getUnreadCount(): ApiResult<MessageCountResponse>

}