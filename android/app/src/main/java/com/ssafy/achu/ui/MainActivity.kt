package com.ssafy.achu.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.achu.core.components.BottomNavBar
import com.ssafy.achu.core.navigation.NavGraph
import com.ssafy.achu.core.theme.AchuTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val activityViewModel: ActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestFcmToken()

        // 액티비티 라이프사이클에 따라 StompService 상태 관리
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 앱이 포그라운드에 있을 때만 실행
                activityViewModel.onAppForeground()
            }
        }

        setContent {
            AchuTheme {
                AchuApp(viewModel = activityViewModel)
            }
        }
    }

}

fun requestFcmToken() {
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "토큰 가져오기 실패", task.exception)
                return@addOnCompleteListener
            }

            // 👉 FCM 토큰 가져오기
            val token = task.result
            Log.d("FCM", "토큰: $token")

        }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AchuApp(viewModel: ActivityViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
    ) { innerPadding ->
        NavGraph(
            navController,
            modifier = Modifier.padding(innerPadding),
            activityViewModel = viewModel
        )
    }
}