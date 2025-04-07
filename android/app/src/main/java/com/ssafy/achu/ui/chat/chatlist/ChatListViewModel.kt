package com.ssafy.achu.ui.chat.chatlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.chatRepository
import com.ssafy.achu.core.ApplicationClass.Companion.json
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.stompService
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.chat.ChatRoomResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "ChatListViewModel"

class ChatListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    init {
        getChatRooms()
    }

    private fun getChatRooms() {
        viewModelScope.launch {
            chatRepository.getChatRooms()
                .onSuccess { response ->
                    Log.d(TAG, "getChatRooms: $response")
                    if (response.result == SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                chatRooms = response.data
                            )
                        }
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "getChatRooms errorResponse: $errorResponse")
                    Log.d(TAG, "getChatRooms error: ${it.message}")
                }
        }
    }

    fun subscribeToChatRooms(userId: Int) {
        viewModelScope.launch {
            stompService.subscribeToDestination(
                "/read/chat/users/$userId/rooms/update"
            ).onSuccess { response ->
                Log.d(TAG, "subscribeToChatRooms: success")
                response?.let { data ->
                    data.collect() {
                        val chatRoom = json.decodeFromString<ChatRoomResponse>(it.bodyAsText)
                        Log.d(TAG, "subscribeToChatRooms: $chatRoom")
                        val mutableChatRooms = uiState.value.chatRooms.toMutableList()
                        mutableChatRooms.removeAll() { cr -> cr.id == chatRoom.id }
                        mutableChatRooms.add(0, chatRoom)
                        _uiState.update { currentState ->
                            currentState.copy(
                                chatRooms = mutableChatRooms
                            )
                        }
                    }
                }
            }.onFailure {
                Log.d(TAG, "subscribeToChatRooms: ${it.message}")
            }
        }
    }
}