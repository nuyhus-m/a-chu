package com.ssafy.achu.ui

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White

private const val SplashWaitTime: Long = 2000

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AchuTheme {
                SplashScreen(
                    viewModel = viewModel,
                    onNavigateToMainActivity = { navigateToMainActivity() },
                    onNavigateToAuthActivity = { navigateToAuthActivity() }
                )
            }
        }
    }

    private fun navigateToMainActivity() {
        val options = ActivityOptions.makeCustomAnimation(this, 0, 0)
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also { intent ->
            startActivity(intent, options.toBundle())
            finish()
        }
    }

    private fun navigateToAuthActivity() {
        val options = ActivityOptions.makeCustomAnimation(this, 0, 0)
        Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also { intent ->
            startActivity(intent, options.toBundle())
            finish()
        }
    }
}

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavigateToMainActivity: () -> Unit,
    onNavigateToAuthActivity: () -> Unit
) {

    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        kotlinx.coroutines.delay(SplashWaitTime)
        viewModel.checkAutoLogin()
    }

    LaunchedEffect(loginState) {
        when (loginState) {
            SplashViewModel.LoginState.Authenticated -> onNavigateToMainActivity()
            SplashViewModel.LoginState.NotAuthenticated -> onNavigateToAuthActivity()
            SplashViewModel.LoginState.Loading -> {}
        }
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