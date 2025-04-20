package com.ssafy.achu.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.achu.ui.auth.findaccount.FindAccountScreen
import com.ssafy.achu.ui.auth.signin.SignInScreen
import com.ssafy.achu.ui.auth.signup.SignUpScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoute.SignIn,
        modifier = modifier
    ) {
        composable<AuthRoute.SignIn> {
            SignInScreen(
                onNavigateToSignUp = { navController.navigate(AuthRoute.SignUp) }
            )
        }

        composable<AuthRoute.SignUp> {
            SignUpScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<AuthRoute.FindAccount> {
            FindAccountScreen()
        }
    }
}