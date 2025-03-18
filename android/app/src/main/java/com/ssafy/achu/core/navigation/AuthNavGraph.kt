package com.ssafy.achu.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.achu.core.navigation.Routes.SIGN_IN
import com.ssafy.achu.ui.signin.SignInScreen
import com.ssafy.achu.ui.signin.SignInViewModel

object Routes {
    const val SIGN_IN = "signin"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SIGN_IN,
        modifier = modifier
    ) {
        composable(route = SIGN_IN) {
            val viewModel: SignInViewModel = viewModel()
            SignInScreen(viewModel = viewModel)
        }
    }
}