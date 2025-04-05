package com.ssafy.achu.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.achu.core.components.BottomNavBar
import com.ssafy.achu.core.navigation.BottomNavRoute
import com.ssafy.achu.core.navigation.NavGraph
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White

private const val TAG = "MainActivity_안주현"

class MainActivity : ComponentActivity() {

    private val activityViewModel: ActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawableResource(android.R.color.white)
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
            AchuTheme{
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isMyPage = currentRoute == BottomNavRoute.MyPage::class.qualifiedName

    // 현재 경로에 따라 상태바 색상 설정
    if (isMyPage) {
        SetStatusBarColor(color = Color.Transparent, darkIcons = false)
    } else {
        SetStatusBarColor(color = Color.White, darkIcons = true)
    }

    LaunchedEffect(targetRoute) {
        if (!targetRoute.isNullOrEmpty()) {
            navController.navigate(targetRoute)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController, viewModel)
        },
    ) { innerPadding ->
        val finalModifier = if (isMyPage) {
            // 마이페이지에서는 상단 패딩을 제거하고 좌우 및 하단 패딩만 적용
            Modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                bottom = innerPadding.calculateBottomPadding()
            )
        } else {
            // 다른 페이지에서는 모든 패딩 적용
            Modifier.padding(innerPadding).background(White)
        }

        NavGraph(
            navController = navController,
            modifier = finalModifier,
            activityViewModel = viewModel
        )
    }
}

@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, color) {
        // 상태바 색상 및 아이콘 색상 설정
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons
        )

        onDispose {}
    }
}