package com.ssafy.achu.ui


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import kotlinx.coroutines.delay

private const val TAG = "MainActivity_안주현"

class MainActivity : ComponentActivity() {
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS

            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), 1001)
            } else {
                Log.d("Permission", "알림 권한 이미 허용됨")
            }
        } else {
            Log.d("Permission", "Android 13 미만은 권한 필요 없음")
        }
    }

    private val activityViewModel: ActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawableResource(android.R.color.white)
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        requestFcmToken()

        activityViewModel.getCategoryList()
        activityViewModel.getUserinfo()
        activityViewModel.getBabyList()
        requestNotificationPermission()


        val targetRoute = intent?.getStringExtra("targetRoute")
        val requestId = intent?.getStringExtra("requestId") ?: ""
        val type = intent?.getStringExtra("type") ?: ""
        Log.d(TAG, "onCreate: ${requestId}")
        Log.d(TAG, "onCreate: ${targetRoute}")


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

    // 화면 전환 중인지 상태 추적
    var isNavigating by remember { mutableStateOf(false) }

    // 현재 경로에 따라 상태바 색상 설정
    if (isMyPage) {
        SetStatusBarColor(color = Color.Transparent, darkIcons = false)
    } else {
        SetStatusBarColor(color = Color.White, darkIcons = true)
    }

    LaunchedEffect(targetRoute) {
        if (!targetRoute.isNullOrEmpty()) {
            // 화면 전환 시작
            isNavigating = true
            navController.navigate(targetRoute)
            // 화면 전환 애니메이션 시간을 고려한 딜레이
            delay(300)
            isNavigating = false
        }
    }

    // NavController의 이동 상태 감지하여 잔상 클릭 방지
    val currentDestination by navController.currentBackStackEntryFlow.collectAsState(initial = null)
    LaunchedEffect(currentDestination) {
        isNavigating = true
        delay(350) // 화면 전환 애니메이션 시간 고려
        isNavigating = false
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
            Modifier
                .padding(innerPadding)
                .background(White)
        }

        // 화면 전환 중인 경우 사용자 입력 차단
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            NavGraph(
                navController = navController,
                modifier = finalModifier,
                activityViewModel = viewModel
            )

            // 화면 전환 중일 때 투명한 오버레이로 클릭 방지
            if (isNavigating) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .clickable(enabled = false) { }
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                // 모든 포인터 이벤트 소비
                                while (true) {
                                    awaitPointerEvent().changes.forEach { it.consume() }
                                }
                            }
                        }
                )
            }
        }
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





