package com.ssafy.achu

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.BottomNavBar
import com.ssafy.achu.core.navigation.BottomNavScreen.Companion.CHAT_LIST
import com.ssafy.achu.core.navigation.BottomNavScreen.Companion.HOME
import com.ssafy.achu.core.navigation.BottomNavScreen.Companion.MEMORY_LIST
import com.ssafy.achu.core.navigation.BottomNavScreen.Companion.MY_PAGE
import com.ssafy.achu.core.navigation.BottomNavScreen.Companion.MY_TRADE_LIST
import com.ssafy.achu.core.navigation.BottomNavScreen.Companion.PRODUCT_LIST
import com.ssafy.achu.core.navigation.NavGraph

private const val TAG = "AchuApp"

@Composable
fun AchuApp() {
    val navController = rememberNavController()
    val onBackClick: () -> Unit = { navController.popBackStack() }

    val currentBackStack by navController.currentBackStackEntryAsState()
    // 현재 화면 루트
    val currentScreenRoute by remember {
        derivedStateOf {
            currentBackStack?.destination?.route ?: HOME
        }
    }
    Log.d(TAG, "현재 화면 루트: $currentScreenRoute")

    // BottomBar 표시 여부
    val isBottomBarVisible = when (currentScreenRoute) {
        HOME, PRODUCT_LIST, MEMORY_LIST, CHAT_LIST, MY_PAGE -> true
        else -> false
    }

    Scaffold(
        topBar = {
            when (currentScreenRoute) {
                CHAT_LIST -> BasicTopAppBar(
                    title = stringResource(R.string.chat_list),
                    onBackClick = onBackClick
                )

                else -> {}
            }
        },
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavBar(navController)
            }
        },
    ) { innerPadding ->
        NavGraph(navController, Modifier.padding(innerPadding))
    }
}