package com.ssafy.achu.core

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "새로운 토큰: $token")

        // 👉 서버로 토큰 전송
        sendTokenToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "푸시 메시지 수신: ${remoteMessage.notification?.body}")
    }

    private fun sendTokenToServer(token: String) {
        // 서버 API로 토큰 전송하는 로직 (Retrofit 등 활용)
        Log.d("FCM", "토큰을 서버로 전송: $token")
    }
}
