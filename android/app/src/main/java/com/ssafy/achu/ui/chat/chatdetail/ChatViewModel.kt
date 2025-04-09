package com.ssafy.achu.ui.chat.chatdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.achu.core.ApplicationClass.Companion.chatRepository
import com.ssafy.achu.core.ApplicationClass.Companion.productRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.stompService
import com.ssafy.achu.core.navigation.Route
import com.ssafy.achu.core.util.Constants.SOLD
import com.ssafy.achu.core.util.Constants.SUCCESS
import com.ssafy.achu.core.util.Constants.TEXT
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.database.SharedPreferencesUtil
import com.ssafy.achu.data.model.chat.ChatRoomRequest
import com.ssafy.achu.data.model.chat.Goods
import com.ssafy.achu.data.model.chat.MessageIdRequest
import com.ssafy.achu.data.model.chat.Partner
import com.ssafy.achu.data.model.chat.SendChatRequest
import com.ssafy.achu.data.model.product.BuyerIdRequest
import com.ssafy.achu.data.model.product.ProductDetailResponse
import com.ssafy.achu.data.model.product.Seller
import com.ssafy.achu.data.network.StompService
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

    private var roomId = savedStateHandle.toRoute<Route.Chat>().roomId

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    object ChatStateHolder {
        var currentRoomId: Int? = null
    }

    fun connectToStompServer() {
        viewModelScope.launch {
            stompService.connect()
            stompService.subscribeToMessage("/read/chat/rooms/$roomId/messages")
            stompService.subscribeToMessageRead("/read/chat/rooms/$roomId/messages/read")
        }

        viewModelScope.launch {
            stompService.messageFlow.collect { message ->
                Log.d(TAG, "subscribeToMessage: $message")
                _uiState.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + message
                    )
                }
                sendMessageRead()
            }
        }

        viewModelScope.launch {
            stompService.messageIdFlow.collect { messageId ->
                Log.d(TAG, "subscribeToMessageRead: $messageId")
                if (messageId.userId == (uiState.value.partner?.id ?: 0)) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            lastReadMessageId = messageId.lastUnreadMessageId
                        )
                    }
                }
            }
        }
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
    fun checkChatRoomExistence() {
        viewModelScope.launch {
            chatRepository.checkChatRoomExistence(
                uiState.value.goods!!.id,
                uiState.value.partner!!.id
            )
                .onSuccess { response ->
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
                            connectToStompServer()
                            getChatListInfo()
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
                    goodsId = uiState.value.goods!!.id,
                    sellerId = uiState.value.partner!!.id,
                    content = uiState.value.inputText
                )
            ).onSuccess { response ->
                Log.d(TAG, "createChatRoom: $response")
                if (response.result == SUCCESS) {
                    roomId = response.data.id
                    connectToStompServer()
                    getChatListInfo()
                    _uiState.update {
                        it.copy(
                            hasChatRoom = true,
                            isFirst = false,
                            inputText = ""
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

    fun updateGoods(product: ProductDetailResponse) {
        _uiState.update { currentState ->
            currentState.copy(
                goods = Goods(
                    id = product.id,
                    title = product.title,
                    thumbnailImageUrl = product.imgUrls[0],
                    price = product.price,
                    tradeStatus = product.tradeStatus
                )
            )
        }
    }

    fun updatePartner(seller: Seller) {
        _uiState.update { currentState ->
            currentState.copy(
                partner = Partner(
                    id = seller.id,
                    nickname = seller.nickname,
                    profileImageUrl = seller.imgUrl
                )
            )
        }
    }

    // 거래 완료
    fun completeTrade() {
        viewModelScope.launch {
            productRepository.completeTrade(
                uiState.value.goods!!.id,
                BuyerIdRequest(uiState.value.partner?.id ?: 0)
            ).onSuccess { response ->
                Log.d(TAG, "completeTrade: $response")
                if (response.result == SUCCESS) {
                    showSoldDialog(false)
                    _toastMessage.emit("거래가 완료되었습니다.")
                    getChatListInfo()
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

    // 채팅 화면에 필요한 모든 데이터 조회

     fun getChatListInfo() {

        viewModelScope.launch {
            chatRepository.getChatListInfo(roomId)
                .onSuccess { response ->
                    Log.d(TAG, "getChatListInfo: ${response.data}")
                    if (response.result == SUCCESS) {
                        _uiState.value = _uiState.value.copy(
                            partner = response.data.chatRoom.partner,
                            goods = response.data.chatRoom.goods,
                            messages = response.data.messages,
                            lastReadMessageId = response.data.partnerLastReadMessageId,
                            isSeller = response.data.isUserSeller,
                            isSold = response.data.chatRoom.goods.tradeStatus == SOLD
                        )
                        sendMessageRead()
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "getChatListInfo errorResponse: $errorResponse")
                    Log.d(TAG, "getChatListInfo error: ${it.message}")
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
            )
        }
    }

    // 메세지 읽음 상태 전송
    private fun sendMessageRead() {
        viewModelScope.launch {
            stompService.sendRequest(
                "/send/chat/rooms/$roomId/messages/read",
                MessageIdRequest(lastReadMessageId = uiState.value.messages.last().id)
            )
        }
    }

    fun cancelStomp() {
        viewModelScope.launch {
            stompService.disconnect()
        }
    }
}
