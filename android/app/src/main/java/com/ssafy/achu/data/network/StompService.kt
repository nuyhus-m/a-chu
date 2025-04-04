package com.ssafy.achu.data.network

import android.util.Log
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.achu.data.model.chat.MessageIdRequest
import com.ssafy.achu.data.model.chat.SendChatRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompReceipt
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.conversions.kxserialization.convertAndSend
import org.hildan.krossbow.stomp.conversions.kxserialization.json.withJsonConversions
import org.hildan.krossbow.stomp.frame.StompFrame
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient
import java.util.concurrent.atomic.AtomicBoolean

private const val TAG = "StompService"
private const val STOMP_URL = "wss://api.a-chu.dukcode.org/chat/websocket"
private const val MAX_RECONNECT_ATTEMPTS = 5
private const val INITIAL_RECONNECT_DELAY = 1000L  // 1초
private const val MAX_RECONNECT_DELAY = 30000L     // 30초

class StompService {

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private var session: StompSession? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var reconnectJob: Job? = null
    private var reconnectAttempts = 0
    private val isReconnecting = AtomicBoolean(false)
    private val activeSubscriptions = mutableMapOf<String, Flow<StompFrame.Message>?>()

    // 연결 상태
    sealed class ConnectionState {
        data object Connected : ConnectionState()
        data object Connecting : ConnectionState()
        data object Disconnected : ConnectionState()
        data class Error(val message: String) : ConnectionState()
        data class Reconnecting(val attempt: Int) : ConnectionState()
    }

    init {
        // 연결 상태 모니터링
        scope.launch {
            connectionState.collect { state ->
                when (state) {
                    is ConnectionState.Error -> {
                        // 자동 재연결 시도
                        if (!isReconnecting.get()) {
                            startReconnection()
                        }
                    }

                    else -> { /* 다른 상태에 대해서는 특별한 처리 없음 */
                    }
                }
            }
        }
    }

    // STOMP 연결
    suspend fun connect() {
        runCatching {
            // 이미 연결 중이거나 연결된 상태면 반환
            if (connectionState.value is ConnectionState.Connecting ||
                connectionState.value is ConnectionState.Connected ||
                connectionState.value is ConnectionState.Reconnecting
            ) {
                return
            }

            _connectionState.value = ConnectionState.Connecting

            // OkHttp 웹소켓 클라이언트로 STOMP 클라이언트 생성
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()
            val wsClient = OkHttpWebSocketClient(okHttpClient)
            val stompClient = StompClient(wsClient)

            // STOMP 세션 연결
            session = stompClient.connect(
                STOMP_URL,
                customStompConnectHeaders = mapOf(
                    "Authorization" to "Bearer ${sharedPreferencesUtil.getTokens()?.accessToken}"
                )
            ).withJsonConversions()

            // 세션 연결 성공 시 세션 해제 감지를 위한 모니터링 시작
            monitorSessionDisconnection()
        }.onSuccess {
            _connectionState.value = ConnectionState.Connected
            reconnectAttempts = 0  // 연결 성공 시 재시도 횟수 초기화
            Log.d(TAG, "connect: STOMP 연결 성공")

            // 기존 구독 복구
            restoreSubscriptions()
        }.onFailure { e ->
            _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
            Log.d(TAG, "connect error: ${e.message}")
        }
    }

    // 세션 해제 감지를 위한 모니터링
    private fun monitorSessionDisconnection() {
        session?.let {
            scope.launch {
                try {
                    Log.d(TAG, "monitorSessionDisconnection: ")
                } catch (e: Exception) {
                    // 예외 발생 시 연결이 끊어진 것으로 간주
                    Log.d(TAG, "Session disconnected: ${e.message}")
                    _connectionState.value = ConnectionState.Error("Connection lost: ${e.message}")
                }
            }
        }
    }

    // 자동 재연결 시작
    private fun startReconnection() {
        if (isReconnecting.compareAndSet(false, true)) {
            reconnectJob?.cancel()
            reconnectJob = scope.launch {
                while (isActive && reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
                    reconnectAttempts++
                    _connectionState.value = ConnectionState.Reconnecting(reconnectAttempts)

                    // 지수 백오프로 딜레이 계산 (최대값 제한)
                    val delay = (INITIAL_RECONNECT_DELAY * (1 shl (reconnectAttempts - 1)))
                        .coerceAtMost(MAX_RECONNECT_DELAY)

                    Log.d(TAG, "Reconnect attempt $reconnectAttempts after ${delay}ms")
                    delay(delay)

                    try {
                        connect()
                        if (connectionState.value is ConnectionState.Connected) {
                            isReconnecting.set(false)
                            Log.d(TAG, "Reconnection successful")
                            break
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "Reconnection attempt failed: ${e.message}")
                    }
                }

                if (reconnectAttempts >= MAX_RECONNECT_ATTEMPTS) {
                    Log.d(TAG, "Max reconnection attempts reached")
                    _connectionState.value =
                        ConnectionState.Error("Max reconnection attempts reached")
                    isReconnecting.set(false)
                }
            }
        }
    }

    // 기존 구독 복구
    private suspend fun restoreSubscriptions() {
        if (activeSubscriptions.isNotEmpty()) {
            Log.d(TAG, "Restoring ${activeSubscriptions.size} subscriptions")
            activeSubscriptions.forEach { (roomId, _) ->
                subscribeToMessage(roomId)
                subscribeToReadStatus(roomId)
            }
        }
    }

    // 메시지 전송
    suspend fun sendMessage(
        roomId: String,
        sendChatRequest: SendChatRequest
    ): Result<StompReceipt?> {
        return runCatching {
            val currentSession =
                session ?: throw IllegalStateException("STOMP session is not initialized")
            currentSession.withJsonConversions().convertAndSend<SendChatRequest>(
                StompSendHeaders(
                    destination = "/send/chat/rooms/$roomId/messages",
                    customHeaders = mapOf(
                        "Authorization" to "Bearer ${sharedPreferencesUtil.getTokens()?.accessToken}"
                    )
                ),
                sendChatRequest
            )
        }
    }

    // 메시지 수신
    suspend fun subscribeToMessage(roomId: String): Result<Flow<StompFrame.Message>?> {
        return runCatching {
            val currentSession =
                session ?: throw IllegalStateException("STOMP session is not initialized")
            val subscription = currentSession.subscribe(
                StompSubscribeHeaders(
                    destination = "/read/chat/rooms/$roomId/messages",
                    customHeaders = mapOf(
                        "Authorization" to "Bearer ${sharedPreferencesUtil.getTokens()?.accessToken}"
                    )
                )
            )
            // 구독 정보 저장
            activeSubscriptions[roomId] = subscription
            subscription
        }
    }

    // 읽음 상태 업데이트
    suspend fun sendReadStatus(
        roomId: String,
        messageIdRequest: MessageIdRequest
    ): Result<StompReceipt?> {
        return runCatching {
            val currentSession =
                session ?: throw IllegalStateException("STOMP session is not initialized")
            currentSession.withJsonConversions().convertAndSend<MessageIdRequest>(
                StompSendHeaders(
                    destination = "/send/chat/rooms/$roomId/messages/read",
                    customHeaders = mapOf(
                        "Authorization" to "Bearer ${sharedPreferencesUtil.getTokens()?.accessToken}"
                    )
                ),
                messageIdRequest
            )
        }
    }

    // 읽음 상태 구독
    suspend fun subscribeToReadStatus(roomId: String): Result<Flow<StompFrame.Message>?> {
        return runCatching {
            val currentSession =
                session ?: throw IllegalStateException("STOMP session is not initialized")
            currentSession.subscribe(
                StompSubscribeHeaders(
                    destination = "/read/chat/rooms/$roomId/messages/read",
                    customHeaders = mapOf(
                        "Authorization" to "Bearer ${sharedPreferencesUtil.getTokens()?.accessToken}"
                    )
                )
            )
        }
    }

    // 연결 종료
    private suspend fun disconnect() {
        runCatching {
            // 이미 연결 해제된 상태면 반환
            if (connectionState.value is ConnectionState.Disconnected) {
                return
            }

            // 재연결 작업 취소
            reconnectJob?.cancel()
            isReconnecting.set(false)

            // 모든 구독 정보 초기화
            activeSubscriptions.clear()

            session?.disconnect()
            session = null
        }.onSuccess {
            _connectionState.value = ConnectionState.Disconnected
            Log.d(TAG, "disconnect: STOMP 연결 종료")
        }.onFailure { e ->
            _connectionState.value = ConnectionState.Error(e.message ?: "Disconnect error")
            Log.d(TAG, "disconnect: ${e.message}")
        }
    }

    // 서비스 정리
    fun cleanup() {
        scope.launch {
            disconnect()
        }
        reconnectJob?.cancel()
        scope.cancel()
    }
}