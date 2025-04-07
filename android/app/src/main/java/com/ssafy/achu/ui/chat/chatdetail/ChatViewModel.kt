package com.ssafy.achu.ui.chat.chatdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.achu.core.ApplicationClass.Companion.chatRepository
import com.ssafy.achu.core.ApplicationClass.Companion.json
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.stompService
import com.ssafy.achu.core.navigation.Route
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.Constants.TEXT
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.chat.ChatRoomRequest
import com.ssafy.achu.data.model.chat.Message
import com.ssafy.achu.data.model.chat.MessageIdRequest
import com.ssafy.achu.data.model.chat.MessageIdResponse
import com.ssafy.achu.data.model.chat.Partner
import com.ssafy.achu.data.model.chat.SendChatRequest
import com.ssafy.achu.data.model.product.BuyerIdRequest
import com.ssafy.achu.data.model.product.ProductDetailResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "ChatViewModel"

class ChatViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val chat = savedStateHandle.toRoute<Route.Chat>()
    private var roomId = chat.roomId

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    init {
        if (roomId == -1) {
            checkChatRoomExistence()
        } else {
            setMessages()
        }
    }

    private fun setMessages() {
        getMessages()
        subscribeToMessage()
        subscribeToMessageRead()
    }

    fun updateInputText(newText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                inputText = newText
            )
        }
    }

    fun showSoldDialog(isShow: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isShowSoldDialog = isShow
            )
        }
    }

    // 채팅방 존재 여부 확인
    private fun checkChatRoomExistence() {
        viewModelScope.launch {
            chatRepository.checkChatRoomExistence(
                uiState.value.product?.id ?: 0,
                uiState.value.partner?.id ?: 0
            ).onSuccess { response ->
                Log.d(TAG, "checkChatRoomExistence: $response")
                if (response.result == SUCCESS) {
                    if (response.data.id == null) {
                        _uiState.update {
                            it.copy(
                                hasChatRoom = false
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                hasChatRoom = true
                            )
                        }
                        roomId = response.data.id
                        setMessages()
                    }
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "checkChatRoomExistence errorResponse: $errorResponse")
                Log.d(TAG, "checkChatRoomExistence error: ${it.message}")
            }
        }
    }

    // 채팅방 생성
    fun createChatRoom() {
        viewModelScope.launch {
            chatRepository.createChatRoom(
                ChatRoomRequest(
                    goodsId = uiState.value.product?.id ?: 0,
                    sellerId = uiState.value.partner?.id ?: 0,
                    content = uiState.value.inputText
                )
            ).onSuccess { response ->
                Log.d(TAG, "createChatRoom: $response")
                if (response.result == SUCCESS) {
                    roomId = response.data.id
                    setMessages()
                    _uiState.update {
                        it.copy(
                            isFirst = false
                        )
                    }
                }
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "createChatRoom errorResponse: $errorResponse")
                Log.d(TAG, "createChatRoom error: ${it.message}")
            }
        }
    }

    // 물품 정보 조회
    private fun getProductDetail(productId: Int) {
        viewModelScope.launch {
            productRepository.getProductDetail(productId)
                .onSuccess { response ->
                    Log.d(TAG, "getProductDetail: $response")
                    if (response.result == SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                product = response.data
                            )
                        }
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "getProductDetail errorResponse: $errorResponse")
                    Log.d(TAG, "getProductDetail error: ${it.message}")
                }
        }
    }

    fun updateProduct(product: ProductDetailResponse) {
        _uiState.update { currentState ->
            currentState.copy(
                product = product
            )
        }
    }

    fun updatePartner(partner: Partner) {
        _uiState.update { currentState ->
            currentState.copy(
                partner = partner
            )
        }
    }

    fun completeTrade() {
        viewModelScope.launch {
            productRepository.completeTrade(
                uiState.value.product?.id ?: 0,
                BuyerIdRequest(uiState.value.partner?.id ?: 0)
            )
                .onSuccess { response ->
                    Log.d(TAG, "completeTrade: $response")
                    if (response.result == SUCCESS) {
                        showSoldDialog(false)
                        _toastMessage.emit("거래가 완료되었습니다.")
                        getProductDetail(uiState.value.product?.id ?: 0)
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "completeTrade errorResponse: $errorResponse")
                    Log.d(TAG, "completeTrade error: ${it.message}")
                    showSoldDialog(false)
                    _toastMessage.emit("거래가 완료되지 않았습니다.")
                }
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
                            val data =
                                json.decodeFromString<MessageIdResponse>(it.bodyAsText)
                            Log.d(TAG, "subscribeToMessageRead: $data")
                            if (data.userId == (uiState.value.partner?.id ?: 0)) {
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