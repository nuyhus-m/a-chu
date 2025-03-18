package com.ssafy.achu

import android.annotation.SuppressLint
import android.app.ActivityOptions
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val isUserLoggedIn: Boolean = false

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
        val options = ActivityOptions.makeCustomAnimation(this, 0, 0)
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also { intent ->
            startActivity(intent, options.toBundle())
        }
    }

    private fun navigateToSignInActivity() {
        val options = ActivityOptions.makeCustomAnimation(this, 0, 0)
        Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also { intent ->
            startActivity(intent, options.toBundle())
        }
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

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = stringResource(R.string.splash),
            style = AchuTheme.typography.regular16.copy(color = White),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-30).dp) // 중앙에서 위로 30dp 이동
        )
    }
}