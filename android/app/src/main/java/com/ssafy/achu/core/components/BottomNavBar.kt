package com.ssafy.achu.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ssafy.achu.core.navigation.BottomNavScreen.Companion.HOME
import com.ssafy.achu.core.navigation.bottomNavBarScreens
import com.ssafy.achu.core.theme.White

@Composable
fun BottomNavBar(navController: NavHostController) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    // 현재 화면 루트
    val currentRoute by remember {
        derivedStateOf {
            currentBackStack?.destination?.route ?: HOME
        }
    }

    AnimatedVisibility(
        visible = bottomNavBarScreens.map { it.route }.contains(currentRoute),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        NavigationBar(
            containerColor = White
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            bottomNavBarScreens.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(painterResource(screen.icon), contentDescription = null) },
                    label = { Text(stringResource(screen.titleResId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
