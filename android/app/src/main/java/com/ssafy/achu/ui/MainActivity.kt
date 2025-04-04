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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.achu.core.components.BottomNavBar
import com.ssafy.achu.core.navigation.NavGraph
import com.ssafy.achu.core.theme.AchuTheme

private const val TAG = "MainActivity_안주현"

class MainActivity : ComponentActivity() {

    private val activityViewModel: ActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        requestFcmToken()

        val targetRoute = intent?.getStringExtra("targetRoute")
        val requestId = intent?.getStringExtra("requestId") ?: ""
        val type = intent?.getStringExtra("type") ?: ""


      when(targetRoute){
          "ProductDetailScreen" -> activityViewModel.getProductDetail(requestId.toInt())
//          "ChatScreen" -> 여기서 아이디는 넘겨 줘야 하는값

      }




        setContent {
            AchuTheme {
                AchuApp(activityViewModel, targetRoute, requestId)
            }
        }
    }

    private fun requestFcmToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "토큰 가져오기 실패", task.exception)
                    return@addOnCompleteListener
                }

                // 👉 FCM 토큰 가져오기
                val token = task.result
                Log.d("FCM", "토큰: $token")
                activityViewModel.updateFcmToken(token)

            }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AchuApp(viewModel: ActivityViewModel, targetRoute: String?, requestId: String) {
    val navController = rememberNavController()

    LaunchedEffect(targetRoute) {
        if (!targetRoute.isNullOrEmpty()) {
            Log.d(TAG, "🔄 네비게이션 이동: $targetRoute")
            navController.navigate(targetRoute)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController, viewModel)
        },
    ) { innerPadding ->
        NavGraph(
            navController,
            modifier = Modifier.padding(innerPadding),
            activityViewModel = viewModel
        )
    }
}