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
        startDestination = BottomNavRoute.Home
    ) {


        // 바텀바 화면들
        composable<BottomNavRoute.Home> {
            HomeScreen(
                modifier = modifier,
                viewModel = activityViewModel,
                onNavigateToLikeList = { navController.navigate(route = Route.LikeList) },
                onNavigateToRecommend = { navController.navigate(route = Route.RecommendList) },
                onNavigateToBabyList = { navController.navigate(route = Route.BabyList) },
                onNavigateToProductList = {
                    navController.navigate(
                        route = BottomNavRoute.ProductList(
                            categoryId = 0
                        )
                    )
                }
            )
        }
        composable<BottomNavRoute.ProductList> {
            ProductListScreen(
                modifier = modifier,
                onNavigateToUploadProduct = { navController.navigate(route = Route.UploadProduct) },
                onNavigateToProductDetail = { navController.navigate(route = Route.ProductDetail) }
            )
        }
        composable<BottomNavRoute.MemoryList> {
            MemoryListScreen(
                viewModel = activityViewModel,
                modifier = modifier,
                memoryViewModel = memoryViewModel,
                onNavigateToMemoryDetail = { navController.navigate(route = Route.MemoryDetail) })
        }
        composable<BottomNavRoute.ChatList> {
            ChatListScreen(
                modifier = modifier,
                onNavigateToChat = { navController.navigate(route = Route.Chat) }
            )
        }
        composable<BottomNavRoute.MyPage> {
            MyPageScreen(
                modifier = modifier,
                viewModel = activityViewModel,
                onNavigateToTradeList = { navController.navigate(route = Route.TradeList) },
                onNavigateToLikeList = { navController.navigate(route = Route.LikeList) },
                onNavigateToRecommend = { navController.navigate(route = Route.RecommendList) },
                onNavigateToBabyList = { navController.navigate(route = Route.BabyList) },
                onNavigateToUserInfo = { navController.navigate(route = Route.UserInfo) })
        }

        // 마이페이지 화면들
        composable<Route.TradeList> {
            TradeListScreen()
        }
        composable<Route.LikeList> {
            LikeItemListScreen(
                viewModel = activityViewModel
            )
        }
        composable<Route.RecommendList> {
            RecommendItemScreen()
        }

        composable<Route.UserInfo> {
            UserInfoScreen(
                viewModel = activityViewModel
            )
        }

        // 마이페이지 - 아기정보관리 화면들
        composable<Route.BabyList> {
            BabyListScreen(
                viewModel = activityViewModel,
                onNavigateToBabyDetail = { navController.navigate(route = Route.BabyDetail) }
            )
        }

        // 추억 관련 화면들
        composable<Route.BabyDetail> {
            BabyDetailScreen(
                viewModel = activityViewModel,
            )
        }

        // 추억 관련 화면들
        composable<Route.MemoryUpload> {
            MemoryUploadScreen(
                memoryViewModel = memoryViewModel,
                onNavigateToMemoryDetail = { navController.navigate(route = Route.MemoryDetail) },
            )
        }

        composable<Route.MemoryDetail> {
            MemoryDetailScreen(
                memoryViewModel = memoryViewModel,
                onNavigateToMemoryUpload =
                    { navController.navigate(route = Route.MemoryUpload) }
            )
        }

        // 중고 거래 관련 화면들
        composable<Route.UploadProduct> {
            UploadProductScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Route.ProductDetail> {
            ProductDetailScreen()
        }

        // 채팅 관련 화면들
        composable<Route.Chat> {
            ChatScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

