package com.ssafy.achu.core.navigation

import HomeScreen
import MyPageScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.achu.core.navigation.MyPage.MY_BABY_DETAIL
import com.ssafy.achu.core.navigation.MyPage.MY_BABY_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_INFO
import com.ssafy.achu.core.navigation.MyPage.MY_LIKE_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_MEMORY_DETAIL
import com.ssafy.achu.core.navigation.MyPage.MY_MEMORY_UPLOAD
import com.ssafy.achu.core.navigation.MyPage.MY_RECOMMEND_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_TRADE_LIST
import com.ssafy.achu.ui.chat.ChatListScreen
import com.ssafy.achu.ui.memory.MemoryDetailScreen
import com.ssafy.achu.ui.memory.MemoryListScreen
import com.ssafy.achu.ui.memory.MemoryUploadScreen
import com.ssafy.achu.ui.mypage.BabyDetailScreen
import com.ssafy.achu.ui.mypage.BabyListScreen
import com.ssafy.achu.ui.mypage.LikeItemListScreen
import com.ssafy.achu.ui.mypage.RecommendItemScreen
import com.ssafy.achu.ui.mypage.TradeListScreen
import com.ssafy.achu.ui.mypage.UserInfoScreen
import com.ssafy.achu.ui.product.ProductListScreen

@RequiresApi(Build.VERSION_CODES.O)
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
            MemoryListScreen { navController.navigate(route = "memorydetail") }
        }
        composable(route = BottomNavScreen.ChatList.route) {
            ChatListScreen()
        }
        composable(route = BottomNavScreen.MyPage.route) {
            MyPageScreen(
                onNavigateToTradeList = { navController.navigate(route = "tradeList") },
                onNavigateToLikeList = { navController.navigate(route = "likelist") },
                onNavigateToRecommend = { navController.navigate(route = "recommend") },
                onNavigateToBabyList = { navController.navigate(route = "babylist") },
                onNavigateToUserInfo = { navController.navigate(route = "info") })
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

        composable(MY_INFO) {
            UserInfoScreen()
        }

        composable(MY_BABY_LIST) {
            BabyListScreen(
                onNavigateToBabyDetail = { navController.navigate(route = "babydetail") }
            )
        }

        composable(MY_BABY_DETAIL) {
            BabyDetailScreen()
        }


        composable(MY_MEMORY_UPLOAD) {
            MemoryUploadScreen()
        }

        composable(MY_MEMORY_DETAIL) {
            MemoryDetailScreen(
                onNavigateToMemoryUpload = { navController.navigate(route = "memoryupload") }
            )
        }

    }
}


object MyPage {
    const val MY_TRADE_LIST = "tradelist"
    const val MY_LIKE_LIST = "likelist"
    const val MY_RECOMMEND_LIST = "recommend"
    const val MY_INFO = "info"
    const val MY_BABY_LIST = "babylist"
    const val MY_BABY_DETAIL = "babydetail"
    const val MY_MEMORY_DETAIL = "memorydetail"
    const val MY_MEMORY_UPLOAD = "memoryupload"
}

