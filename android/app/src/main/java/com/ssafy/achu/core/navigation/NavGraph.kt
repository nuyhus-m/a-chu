package com.ssafy.achu.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.achu.ui.chat.ChatListScreen
import com.ssafy.achu.ui.home.HomeScreen
import com.ssafy.achu.ui.memory.MemoryListScreen
import com.ssafy.achu.ui.mypage.MyPageScreen
import com.ssafy.achu.ui.product.ProductListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavScreen.ProductList.route) {
            ProductListScreen()
        }
        composable(route = BottomNavScreen.MemoryList.route) {
            MemoryListScreen()
        }
        composable(route = BottomNavScreen.ChatList.route) {
            ChatListScreen()
        }
        composable(route = BottomNavScreen.MyPage.route) {
            MyPageScreen()
        }
    }
}