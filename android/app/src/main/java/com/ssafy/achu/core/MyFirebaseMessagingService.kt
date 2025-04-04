package com.ssafy.achu.core

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.achu.R
import com.ssafy.achu.core.ApplicationClass.Companion.fcmRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.Token
import com.ssafy.achu.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MyFirebaseMessagingServ_안주현"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "새로운 토큰: $token")
        sendTokenToServer(token)
    }

    private fun sendTokenToServer(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            fcmRepository.updateToken(Token(token)).onSuccess {
                Log.d(TAG, "sendTokenToServer: ${it}, 등록성공")
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "sendTokenToServer: ${errorResponse}")
            }
        }
    }

    @SuppressLint("MissingFirebaseInstanceTokenRefresh")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(TAG, "onMessageReceived: 메세지온다")
        remoteMessage.data?.let { data ->
            val targetFragment = data["targetFragment"] ?: return
            val requestId = data["requestId"] ?: ""
            val type = data["type"] ?: ""
            Log.d(TAG, "onMessageReceived: ${data}")
            val intent = Intent(this, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW
                putExtra("targetRoute", targetFragment)
                putExtra("requestId", requestId)
                putExtra("type", type)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            val notification = NotificationCompat.Builder(this, "channel_id")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(remoteMessage.notification?.title ?: "새로운 알림")
                .setContentText(remoteMessage.notification?.body ?: "확인해 주세요")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notification)
        }
    }
}
