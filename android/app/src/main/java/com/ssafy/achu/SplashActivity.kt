package com.ssafy.achu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val isUserLoggedIn: Boolean = true
//        get() {
//            val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//            return sharedPreferences.getBoolean("is_logged_in", false)
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AchuTheme {
                SplashScreen(onTimeout = {
                    // 로그인 여부에 따라 적절한 액티비티로 이동
                    if (isUserLoggedIn) {
                        navigateToMainActivity()
                    } else {
                        navigateToSignInActivity()
                    }
                })
            }
        }
    }

    private fun navigateToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also { intent ->
            startActivity(intent)
            finish()
        }
    }

    private fun navigateToSignInActivity() {
//        Intent(this, SignInActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }.also { intent ->
//            startActivity(intent)
//            finish()
//        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    // Composable이 재구성(recomposition)되더라도 항상 최신의 onTimeout을 사용
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(key1 = Unit) {
        delay(SplashWaitTime)
        currentOnTimeout()
    }

    Splash()
}

@Preview
@Composable
fun Splash() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = "추억을 기록하는 육아용품 중고거래 앱",
            style = AchuTheme.typography.regular16.copy(color = White),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-30).dp) // 중앙에서 위로 30dp 이동
        )
    }
}