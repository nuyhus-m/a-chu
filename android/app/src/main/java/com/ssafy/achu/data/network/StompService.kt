package com.ssafy.achu.data.network

import android.util.Log
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession
import org.hildan.krossbow.stomp.conversions.kxserialization.convertAndSend
import org.hildan.krossbow.stomp.conversions.kxserialization.json.withJsonConversions
import org.hildan.krossbow.stomp.frame.StompFrame
import org.hildan.krossbow.stomp.headers.StompSendHeaders
import org.hildan.krossbow.stomp.headers.StompSubscribeHeaders
import org.hildan.krossbow.websocket.WebSocketException
import org.hildan.krossbow.websocket.okhttp.OkHttpWebSocketClient

private const val TAG = "StompService"
private const val STOMP_URL = "wss://api.a-chu.dukcode.org/chat-ws/websocket"

class StompService {

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    var session: StompSession? = null
    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Coroutine 예외 발생: ${throwable.message}", throwable)
        }
    )

    // 연결 상태
    sealed class ConnectionState {
        data object Connected : ConnectionState()
        data object Connecting : ConnectionState()
        data object Disconnected : ConnectionState()
        data class Error(val message: String) : ConnectionState()
    }

    // STOMP 연결
    fun connect() {
        scope.launch {
            if (connectionState.value is ConnectionState.Connecting || connectionState.value is ConnectionState.Connected) {
                Log.d(TAG, "이미 연결 중이거나 연결됨. 상태: ${connectionState.value}")
                return@launch
            }

            val token = sharedPreferencesUtil.getTokens()?.accessToken

            if (token.isNullOrBlank()) {
                Log.e(TAG, "Access token이 없어서 연결 시도 중단")
                _connectionState.value = ConnectionState.Error("Access token is missing")
                return@launch
            }

            _connectionState.value = ConnectionState.Connecting
            Log.d(TAG, "STOMP 연결 시도 중...")

            runCatching {
                // OkHttpClient 설정 (WebSocket 핸드셰이크 로그)
                val loggingInterceptor = HttpLoggingInterceptor { message ->
                    Log.d("WS-OkHttp", message)
                }.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                val wsClient = OkHttpWebSocketClient(okHttpClient)
                val stompClient = StompClient(wsClient)

                Log.d(TAG, "WebSocket 클라이언트 및 STOMP 클라이언트 초기화 완료")

                // STOMP 세션 연결
                session = stompClient.connect(
                    STOMP_URL,
                    customStompConnectHeaders = mapOf(
                        "Authorization" to "Bearer $token"
                    )
                ).withJsonConversions()

                _connectionState.value = ConnectionState.Connected
                Log.d(TAG, "✅ STOMP 연결 성공")

            }.onFailure { e ->
                val errorMessage = e.message ?: "Unknown error"
                _connectionState.value = ConnectionState.Error(errorMessage)
                Log.e(TAG, "❌ STOMP 연결 실패: $errorMessage", e)

                when (e) {
                    is WebSocketException -> {
                        Log.e(TAG, "WebSocketException: ${e.message}")
                        e.cause?.let { cause ->
                            Log.e(TAG, "원인: ${cause.message}", cause)
                        }
                    }

                    is java.io.EOFException -> {
                        Log.e(TAG, "EOFException: 서버에서 응답 없이 연결 종료", e)
                    }

                    else -> {
                        Log.e(TAG, "알 수 없는 예외 발생", e)
                    }
                }
            }
        }
    }

    // 구독 함수
    suspend fun subscribeToDestination(destination: String): Result<Flow<StompFrame.Message>?> {
        return runCatching {
            val currentSession =
                session ?: throw IllegalStateException("STOMP session is not initialized")
            currentSession.subscribe(
                StompSubscribeHeaders(
                    destination = destination,
                    customHeaders = mapOf(
                        "Authorization" to "Bearer ${sharedPreferencesUtil.getTokens()?.accessToken}"
                    )
                )
            )
        }
    }

    // 요청 전송 함수
    suspend inline fun <reified T> sendRequest(
        destination: String,
        request: T
    ): Result<Unit> {
        return runCatching {
            val currentSession =
                session ?: throw IllegalStateException("STOMP session is not initialized")
            val a = currentSession.withJsonConversions().convertAndSend(
                StompSendHeaders(
                    destination = destination,
                    customHeaders = mapOf(
                        "Authorization" to "Bearer ${sharedPreferencesUtil.getTokens()?.accessToken}"
                    )
                ),
                request
            )
        }
    }

    // 연결 해제
    fun disconnect() {
        scope.launch {
            runCatching {
                // 이미 연결 해제된 상태면 반환
                if (connectionState.value is ConnectionState.Disconnected) {
                    return@launch
                }

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
    }
}