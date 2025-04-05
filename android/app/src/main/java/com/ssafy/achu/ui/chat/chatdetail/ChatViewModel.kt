package com.ssafy.achu.ui.chat.chatdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.achu.core.ApplicationClass.Companion.chatRepository
import com.ssafy.achu.core.ApplicationClass.Companion.json
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.stompService
import com.ssafy.achu.core.navigation.Route
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.Constants.TEXT
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.chat.Message
import com.ssafy.achu.data.model.chat.MessageIdRequest
import com.ssafy.achu.data.model.chat.MessageIdResponse
import com.ssafy.achu.data.model.chat.SendChatRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "ChatViewModel"

class ChatViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chat = savedStateHandle.toRoute<Route.Chat>()
    private var roomId = chat.roomId
    val productId = chat.productId
    private val partnerId = chat.partnerId

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        if (roomId == -1) {
            // 채팅방 존재 여부 확인
        } else {
            getMessages()
            subscribeToMessage()
            subscribeToMessageRead()
        }
    }

    fun updateInputText(newText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                inputText = newText
            )
        }
    }

    // 메시지 목록 조회
    private fun getMessages() {
        viewModelScope.launch {
            chatRepository.getMessages(roomId)
                .onSuccess { response ->
                    Log.d(TAG, "getMessages: ${response.data}")
                    if (response.result == SUCCESS) {
                        _uiState.value = _uiState.value.copy(
                            messages = response.data
                        )
                        sendMessageRead()
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "getMessages errorResponse: $errorResponse")
                    Log.d(TAG, "getMessages error: ${it.message}")
                }
        }
    }

    // 메시지 전송
    fun sendMessage() {
        viewModelScope.launch {
            stompService.sendRequest(
                "/send/chat/rooms/$roomId/messages",
                SendChatRequest(
                    content = uiState.value.inputText,
                    type = TEXT
                )
            ).onSuccess {
                Log.d(TAG, "sendMessage: success")
            }.onFailure {
                Log.d(TAG, "sendMessage: ${it.message}")
            }
        }
    }

    // 메시지 수신
    private fun subscribeToMessage() {
        viewModelScope.launch {
            stompService.subscribeToDestination("/read/chat/rooms/$roomId/messages")
                .onSuccess { response ->
                    Log.d(TAG, "subscribeToMessage: success")
                    response?.let { body ->
                        body.collect {
                            val data = json.decodeFromString<Message>(it.bodyAsText)
                            Log.d(TAG, "subscribeToMessage: $data")
                            _uiState.update { currentState ->
                                currentState.copy(
                                    messages = currentState.messages + data
                                )
                            }
                            sendMessageRead()
                        }
                    }
                }
        }
    }

    // 메세지 읽음 상태 전송
    private fun sendMessageRead() {
        viewModelScope.launch {
            stompService.sendRequest(
                "/send/chat/rooms/$roomId/messages/read",
                MessageIdRequest(lastReadMessageId = uiState.value.messages.last().id)
            ).onSuccess {
                Log.d(TAG, "sendMessageRead: success")
            }.onFailure {
                Log.d(TAG, "sendMessageRead: ${it.message}")
            }
        }
    }

    // 메세지 읽음 상태 수신
    private fun subscribeToMessageRead() {
        viewModelScope.launch {
            stompService.subscribeToDestination("/read/chat/rooms/$roomId/messages/read")
                .onSuccess { response ->
                    Log.d(TAG, "subscribeToMessageRead: success")
                    response?.let { body ->
                        body.collect {
                            val data = json.decodeFromString<MessageIdResponse>(it.bodyAsText)
                            Log.d(TAG, "subscribeToMessageRead: $data")
                            if (data.userId == partnerId) {
                                _uiState.update { currentState ->
                                    currentState.copy(
                                        lastReadMessageId = data.lastUnreadMessageId
                                    )
                                }
                            }
                        }

                    }
                }.onFailure {
                    Log.d(TAG, "subscribeToMessageRead: ${it.message}")
                }
        }
    }
}