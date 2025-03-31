package com.ssafy.achu.core.navigation

import HomeScreen
import MyPageScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.achu.core.navigation.MyPage.MY_BABY_DETAIL
import com.ssafy.achu.core.navigation.MyPage.MY_BABY_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_CHAT
import com.ssafy.achu.core.navigation.MyPage.MY_INFO
import com.ssafy.achu.core.navigation.MyPage.MY_LIKE_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_MEMORY_DETAIL
import com.ssafy.achu.core.navigation.MyPage.MY_MEMORY_UPLOAD
import com.ssafy.achu.core.navigation.MyPage.MY_PRODUCT_DETAIL
import com.ssafy.achu.core.navigation.MyPage.MY_RECOMMEND_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_TRADE_LIST
import com.ssafy.achu.core.navigation.MyPage.MY_UPLOAD_PRODUCT
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.chat.chatdetail.ChatScreen
import com.ssafy.achu.ui.chat.chatlist.ChatListScreen
import com.ssafy.achu.ui.memory.MemoryDetailScreen
import com.ssafy.achu.ui.memory.MemoryListScreen
import com.ssafy.achu.ui.memory.MemoryUploadScreen
import com.ssafy.achu.ui.memory.MemoryViewModel
import com.ssafy.achu.ui.mypage.baby.BabyDetailScreen
import com.ssafy.achu.ui.mypage.baby.BabyListScreen
import com.ssafy.achu.ui.mypage.likelist.LikeItemListScreen
import com.ssafy.achu.ui.mypage.recommendlist.RecommendItemScreen
import com.ssafy.achu.ui.mypage.tradelist.TradeListScreen
import com.ssafy.achu.ui.mypage.userinfo.UserInfoScreen
import com.ssafy.achu.ui.product.ProductDetailScreen
import com.ssafy.achu.ui.product.productlist.ProductListScreen
import com.ssafy.achu.ui.product.productlist.ProductListViewModel
import com.ssafy.achu.ui.product.uploadproduct.UploadProductScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
    activityViewModel: ActivityViewModel,
    memoryViewModel: MemoryViewModel = viewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route
    ) {


        // 바텀바 화면들
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen(
                modifier = modifier,
                viewModel = activityViewModel,
                onNavigateToLikeList = { navController.navigate(route = MY_LIKE_LIST) },
                onNavigateToRecommend = { navController.navigate(route = MY_RECOMMEND_LIST) },
                onNavigateToBabyList = { navController.navigate(route = MY_BABY_LIST) },
                onNavigateToProductList = { navController.navigate(route = BottomNavScreen.ProductList.route) }
            )
        }
        composable(route = BottomNavScreen.ProductList.route) {
            val viewModel: ProductListViewModel = viewModel()
            ProductListScreen(
                modifier = modifier,
                viewModel = viewModel,
                onNavigateToUploadProduct = { navController.navigate(route = MY_UPLOAD_PRODUCT) },
                onNavigateToProductDetail = { navController.navigate(route = MY_PRODUCT_DETAIL) }
            )
        }
        composable(route = BottomNavScreen.MemoryList.route) {
            MemoryListScreen(
                viewModel = activityViewModel,
                modifier = modifier,
                onNavigateToMemoryDetail = { navController.navigate(route = MY_MEMORY_DETAIL)},
                memoryViewModel = memoryViewModel
            )
        }
        composable(route = BottomNavScreen.ChatList.route) {
            ChatListScreen(
                modifier = modifier,
                onNavigateToChat = { navController.navigate(route = MY_CHAT) }
            )
        }
        composable(route = BottomNavScreen.MyPage.route) {
            MyPageScreen(
                modifier = modifier,
                viewModel = activityViewModel,
                onNavigateToTradeList = { navController.navigate(route = MY_TRADE_LIST) },
                onNavigateToLikeList = { navController.navigate(route = MY_LIKE_LIST) },
                onNavigateToRecommend = { navController.navigate(route = MY_RECOMMEND_LIST) },
                onNavigateToBabyList = { navController.navigate(route = MY_BABY_LIST) },
                onNavigateToUserInfo = { navController.navigate(route = MY_INFO) })
        }

        // 마이페이지 화면들
        composable(MY_TRADE_LIST) {
            TradeListScreen()
        }
        composable(MY_LIKE_LIST) {
            LikeItemListScreen(
                viewModel = activityViewModel,
            )
        }
        composable(MY_RECOMMEND_LIST) {
            RecommendItemScreen()
        }
        composable(MY_INFO) {
            UserInfoScreen(
                viewModel = activityViewModel,
            )
        }

        // 마이페이지 - 아기정보관리 화면들
        composable(MY_BABY_LIST) {
            BabyListScreen(
                viewModel = activityViewModel,
                onNavigateToBabyDetail = { navController.navigate(route = MY_BABY_DETAIL) }
            )
        }
        composable(MY_BABY_DETAIL) {
            BabyDetailScreen(
                viewModel = activityViewModel,
            )
        }

        // 추억 관련 화면들
        composable(MY_MEMORY_UPLOAD) {
            MemoryUploadScreen(
                onNavigateToMemoryDetail = { navController.navigate(route = MY_MEMORY_DETAIL) },
                memoryViewModel = memoryViewModel
            )
        }
        composable(MY_MEMORY_DETAIL) {
            MemoryDetailScreen(
                onNavigateToMemoryUpload = { navController.navigate(route = MY_MEMORY_UPLOAD) },
                memoryViewModel
            )
        }

        // 중고 거래 관련 화면들
        composable(MY_UPLOAD_PRODUCT) {
            UploadProductScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(MY_PRODUCT_DETAIL) {
            ProductDetailScreen()
        }

        // 채팅 관련 화면들
        composable(MY_CHAT) {
            ChatScreen(
                onBackClick = { navController.popBackStack() }
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

    const val MY_PRODUCT_DETAIL = "productdetail"
    const val MY_UPLOAD_PRODUCT = "uploadproduct"
    const val MY_CHAT = "chat"
}

