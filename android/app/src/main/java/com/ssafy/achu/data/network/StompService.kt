package com.ssafy.achu.data.network

import android.util.Log
import com.ssafy.achu.core.ApplicationClass.Companion.json
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
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
import java.util.concurrent.TimeUnit

private const val TAG = "StompService"
private const val STOMP_URL = "wss://api.a-chu.dukcode.org/chat-ws/websocket"
private const val MAX_RETRY_ATTEMPTS = 5
private const val INITIAL_RETRY_DELAY_MS = 1000L

class StompService {

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    var session: StompSession? = null
    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            Log.e(TAG, "Coroutine 예외 발생: ${throwable.message}", throwable)
        }
    )

    private var retryCount = 0
    private var isRetrying = false

    // 연결 상태
    sealed class ConnectionState {
        data object Connected : ConnectionState()
        data object Connecting : ConnectionState()
        data object Disconnected : ConnectionState()
        data class Error(val message: String) : ConnectionState()
    }

    // 세션 활성 상태 확인 함수
    fun isSessionActive(): Boolean {
        return session != null && connectionState.value is ConnectionState.Connected
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

            connectWithRetry(token)
        }
    }

    private suspend fun connectWithRetry(token: String, attempt: Int = 0) {
        if (attempt >= MAX_RETRY_ATTEMPTS) {
            Log.e(TAG, "최대 재시도 횟수에 도달했습니다. 연결 시도를 중단합니다.")
            _connectionState.value = ConnectionState.Error("Maximum retry attempts reached")
            isRetrying = false
            retryCount = 0
            return
        }

        if (attempt > 0) {
            val delayTime = INITIAL_RETRY_DELAY_MS * (1 shl (attempt - 1)) // 지수 백오프
            Log.d(TAG, "재연결 시도 $attempt 전 ${delayTime}ms 대기")
            delay(delayTime)
        }

        _connectionState.value = ConnectionState.Connecting
        Log.d(TAG, "STOMP 연결 시도 중... (시도 ${attempt + 1}/${MAX_RETRY_ATTEMPTS})")

        runCatching {
            // OkHttpClient 설정 (WebSocket 핸드셰이크 로그 및 타임아웃 설정)
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Log.d("WS-OkHttp", message)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .pingInterval(20, TimeUnit.SECONDS) // Keep-alive ping 설정
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

            // 연결 성공 시 상태 업데이트 및 재시도 카운터 초기화
            _connectionState.value = ConnectionState.Connected
            retryCount = 0
            isRetrying = false
            Log.d(TAG, "✅ STOMP 연결 성공")

            // 연결 상태 모니터링
            monitorConnection()

        }.onFailure { e ->
            val errorMessage = e.message ?: "Unknown error"
            _connectionState.value = ConnectionState.Error(errorMessage)
            Log.e(TAG, "❌ STOMP 연결 실패 (시도 ${attempt + 1}): $errorMessage", e)

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

            // 자동 재시도
            connectWithRetry(token, attempt + 1)
        }
    }

    // 연결 상태 모니터링
    private fun monitorConnection() {
        scope.launch {
            while (session != null && connectionState.value is ConnectionState.Connected) {
                try {
                    // 연결 상태 확인을 위한 PING 메시지 전송 대신 상태 확인
                    // 연결 끊어짐이 감지되면 재연결 시도
                    if (connectionState.value !is ConnectionState.Connected) {
                        Log.w(TAG, "연결 상태가 Connected가 아님, 재연결 시도")
                        reconnect()
                        break
                    }

                    // 간단한 STOMP 프레임 테스트
                    try {
                        val pingDestination = "/app/ping"
                        val pingResult = session?.withJsonConversions()?.convertAndSend(
                            StompSendHeaders(destination = pingDestination),
                            "ping"
                        )

                        // ping 성공 시 로그만 남김
                        Log.d(TAG, "연결 상태 확인 ping 성공")
                    } catch (e: Exception) {
                        Log.e(TAG, "연결 상태 확인 중 오류 발생: ${e.message}", e)
                        reconnect()
                        break
                    }

                    delay(30000) // 30초마다 확인
                } catch (e: Exception) {
                    Log.e(TAG, "연결 모니터링 중 오류 발생: ${e.message}", e)
                    reconnect()
                    break
                }
            }
        }
    }

    // 재연결 함수
    fun reconnect() {
        scope.launch {
            if (!isRetrying) {
                isRetrying = true
                session = null
                _connectionState.value = ConnectionState.Disconnected

                val token = sharedPreferencesUtil.getTokens()?.accessToken
                if (!token.isNullOrBlank()) {
                    connectWithRetry(token)
                } else {
                    _connectionState.value =
                        ConnectionState.Error("Access token is missing for reconnection")
                    isRetrying = false
                }
            }
        }
    }

    // 구독 함수
    suspend fun subscribeToDestination(destination: String): Result<Flow<StompFrame.Message>?> {
        return runCatching {
            // 세션이 없거나 연결이 끊어진 경우 먼저 연결 시도
            if (!isSessionActive()) {
                reconnect()
                // 연결이 완료될 때까지 대기
                var retryAttempts = 0
                while (connectionState.value !is ConnectionState.Connected && retryAttempts < 5) {
                    delay(1000)
                    retryAttempts++
                }

                if (connectionState.value !is ConnectionState.Connected) {
                    throw IllegalStateException("Could not establish STOMP connection for subscription")
                }
            }

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
            // 세션이 없거나 연결이 끊어진 경우 먼저 연결 시도
            if (!isSessionActive()) {
                reconnect()
                // 연결이 완료될 때까지 대기
                var retryAttempts = 0
                while (connectionState.value !is ConnectionState.Connected && retryAttempts < 5) {
                    delay(1000)
                    retryAttempts++
                }

                if (connectionState.value !is ConnectionState.Connected) {
                    throw IllegalStateException("Could not establish STOMP connection for sending request")
                }
            }

            val currentSession =
                session ?: throw IllegalStateException("STOMP session is not initialized")
            currentSession.withJsonConversions(json).convertAndSend(
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