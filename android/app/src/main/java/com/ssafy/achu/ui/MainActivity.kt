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
import com.ssafy.achu.core.navigation.Route
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White
import kotlinx.coroutines.delay

private const val TAG = "MainActivity_안주현"

class MainActivity : ComponentActivity() {

    private val activityViewModel: ActivityViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawableResource(android.R.color.white)
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        requestNotificationPermission()
        requestFcmToken(activityViewModel)

        activityViewModel.getCategoryList()
        activityViewModel.getUserinfo()
        activityViewModel.getBabyList()

        val targetRoute = intent?.getStringExtra("targetRoute")
        val requestId = intent?.getStringExtra("requestId") ?: ""
        val type = intent?.getStringExtra("type") ?: ""
        Log.d(TAG, "onCreate: $requestId")
        Log.d(TAG, "onCreate: $targetRoute")

        when (targetRoute) {
            "ProductDetail" -> activityViewModel.getProductDetail(requestId.toInt())
        }

        setContent {
            AchuTheme {
                AchuApp(activityViewModel, targetRoute)
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(permission), 1001)
            } else {
                Log.d("Permission", "알림 권한 이미 허용됨")
            }
        } else {
            Log.d("Permission", "Android 13 미만은 권한 필요 없음")
        }
    }

    private fun requestFcmToken(viewModel: ActivityViewModel) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "토큰 가져오기 실패", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                Log.d("FCM", "토큰: $token")
                viewModel.updateFcmToken(token)
            }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AchuApp(viewModel: ActivityViewModel, targetRoute: String?) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isMyPage = currentRoute == BottomNavRoute.MyPage::class.qualifiedName

    var isNavigating by remember { mutableStateOf(false) }

    if (isMyPage) {
        SetStatusBarColor(color = Color.Transparent, darkIcons = false)
    } else {
        SetStatusBarColor(color = Color.White, darkIcons = true)
    }
    LaunchedEffect(targetRoute) {
        if (targetRoute != null) {
            isNavigating = true
            when (targetRoute) {
                "ProductDetail" -> {
                    navController.navigate(Route.ProductDetail(false))
                }

                "TradeList" -> {
                    navController.navigate(Route.TradeList)
                }

                "Chat" -> {
                    navController.navigate(BottomNavRoute.ChatList)
                }
            }
            delay(300)
            isNavigating = false
        }
    }

    DisposableEffect(Unit) {
        viewModel.connectToStompServer()

        onDispose {
            viewModel.cancelStomp()
        }
    }

    val currentDestination by navController.currentBackStackEntryFlow.collectAsState(initial = null)
    LaunchedEffect(currentDestination) {
        isNavigating = true
        delay(300)
        isNavigating = false
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController, viewModel)
        }
    ) { innerPadding ->
        val finalModifier = if (isMyPage) {
            Modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                bottom = innerPadding.calculateBottomPadding()
            )
        } else {
            Modifier
                .padding(innerPadding)
                .background(White)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            NavGraph(
                navController = navController,
                modifier = finalModifier,
                activityViewModel = viewModel
            )

            if (isNavigating) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .clickable(enabled = false) { }
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
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
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons
        )
        onDispose {}
    }
}
