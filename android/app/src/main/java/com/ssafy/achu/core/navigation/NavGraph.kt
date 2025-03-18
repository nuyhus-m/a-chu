package com.ssafy.achu.core.navigation

import MyPageScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.achu.core.navigation.MyPage.MY_LIKE_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_RECOMMEND_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_TRADE_LIST
import com.ssafy.achu.ui.chat.ChatListScreen
import com.ssafy.achu.ui.home.HomeScreen
import com.ssafy.achu.ui.memory.MemoryListScreen
import com.ssafy.achu.ui.mypage.LikeItemListScreen
import com.ssafy.achu.ui.mypage.RecommendItemScreen
import com.ssafy.achu.ui.mypage.TradeListScreen
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
            MyPageScreen(
                onNavigateToTradeList = { navController.navigate(route = "tradeList") },
                onNavigateToLikeList = { navController.navigate(route = "likelist") },
                onNavigateToRecommend = { navController.navigate(route = "recommend") })
        }

        composable(MY_TRADE_LIST) {
            TradeListScreen()
        }

        composable(MY_LIKE_LIST) {
            LikeItemListScreen()
        }

        composable(MY_RECOMMEND_LIST) {
            RecommendItemScreen()
        }

    }
}


object MyPage {
    const val MY_TRADE_LIST = "tradelist"
    const val MY_LIKE_LIST = "likelist"
    const val MY_RECOMMEND_LIST = "recommend"
}

