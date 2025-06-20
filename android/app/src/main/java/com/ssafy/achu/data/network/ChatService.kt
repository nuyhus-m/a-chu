package com.ssafy.achu.data.network

import com.ssafy.achu.data.model.ApiResult
import com.ssafy.achu.data.model.IdResponse
import com.ssafy.achu.data.model.chat.ChatListResponse
import com.ssafy.achu.data.model.chat.ChatRoomRequest
import com.ssafy.achu.data.model.chat.ChatRoomResponse
import com.ssafy.achu.data.model.chat.MessageCountResponse
import com.ssafy.achu.data.model.chat.RoomIdResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatService {

    @POST("/chat/rooms")
    suspend fun createChatRoom(
        @Body chatRoomRequest: ChatRoomRequest
    ): ApiResult<IdResponse>

    @GET("/chat/rooms")
    suspend fun getChatRooms(): ApiResult<List<ChatRoomResponse>>

    @GET("/chat/unread-count")
    suspend fun getUnreadCount(): ApiResult<MessageCountResponse>

    @GET("/chat/rooms/{roomId}/messages")
    suspend fun getChatListInfo(
        @Path("roomId") roomId: Int
    ): ApiResult<ChatListResponse>

    @GET("/chat/rooms/existence")
    suspend fun checkChatRoomExistence(
        @Query("goodsId") productId: Int,
        @Query("sellerId") sellerId: Int
    ): ApiResult<RoomIdResponse>

}