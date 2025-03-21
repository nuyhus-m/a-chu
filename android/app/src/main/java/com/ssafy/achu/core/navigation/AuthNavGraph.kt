package com.ssafy.achu.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.achu.core.navigation.Routes.FIND_ACCOUNT
import com.ssafy.achu.core.navigation.Routes.SIGN_IN
import com.ssafy.achu.core.navigation.Routes.SIGN_UP
import com.ssafy.achu.ui.findaccount.FindAccountScreen
import com.ssafy.achu.ui.findaccount.FindAccountViewModel
import com.ssafy.achu.ui.signin.SignInScreen
import com.ssafy.achu.ui.signin.SignInViewModel
import com.ssafy.achu.ui.signup.SignUpScreen
import com.ssafy.achu.ui.signup.SignUpViewModel

object Routes {
    const val SIGN_IN = "signin"
    const val SIGN_UP = "signup"
    const val FIND_ACCOUNT = "findaccount"
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
            SignInScreen(
                viewModel = viewModel,
                onNavigateToSignUp = { navController.navigate(SIGN_UP) },
                onNavigateToFindAccount = { navController.navigate(FIND_ACCOUNT) }
            )
        }

        composable(route = SIGN_UP) {
            val viewModel: SignUpViewModel = viewModel()
            SignUpScreen(viewModel = viewModel)
        }

        composable(route = FIND_ACCOUNT) {
            val viewModel: FindAccountViewModel = viewModel()
            FindAccountScreen(
                viewModel = viewModel
            )
        }
    }
}