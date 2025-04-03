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
import androidx.navigation.toRoute
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.chat.chatdetail.ChatScreen
import com.ssafy.achu.ui.chat.chatlist.ChatListScreen
import com.ssafy.achu.ui.memory.memorydetail.MemoryDetailScreen
import com.ssafy.achu.ui.memory.MemoryListScreen
import com.ssafy.achu.ui.memory.memoryupload.MemoryUploadScreen
import com.ssafy.achu.ui.mypage.baby.BabyDetailScreen
import com.ssafy.achu.ui.mypage.baby.BabyListScreen
import com.ssafy.achu.ui.mypage.likelist.LikeItemListScreen
import com.ssafy.achu.ui.mypage.recommendlist.RecommendItemScreen
import com.ssafy.achu.ui.mypage.tradelist.TradeListScreen
import com.ssafy.achu.ui.mypage.userinfo.UserInfoScreen
import com.ssafy.achu.ui.product.productdetail.ProductDetailScreen
import com.ssafy.achu.ui.product.productlist.ProductListScreen
import com.ssafy.achu.ui.product.uploadproduct.UploadProductScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier,
    activityViewModel: ActivityViewModel,
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
                onNavigateToProductList = { categoryId ->
                    navController.navigate(
                        route = BottomNavRoute.ProductList(
                            categoryId = categoryId
                        )
                    )
                },
                onNavigateToProductDetail = {
                    navController.navigate(
                        route = Route.ProductDetail(
                            isPreview = false
                        )
                    )
                }
            )
        }
        composable<BottomNavRoute.ProductList> {
            ProductListScreen(
                modifier = modifier,
                activityViewModel = activityViewModel,
                onNavigateToUploadProduct = {
                    navController.navigate(
                        route = Route.UploadProduct(
                            isModify = false
                        )
                    )
                },
                onNavigateToProductDetail = {
                    navController.navigate(
                        route = Route.ProductDetail(
                            isPreview = false
                        )
                    )
                }
            )
        }
        composable<BottomNavRoute.MemoryList> {
            MemoryListScreen(
                viewModel = activityViewModel,
                modifier = modifier,
                onNavigateToMemoryDetail = { memoryId, babyId ->
                    navController.navigate(
                        route = Route.MemoryDetail(memoryId = memoryId, babyId = babyId)
                    )
                }
            )
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
            TradeListScreen(

                activityViewModel = activityViewModel,
                onNavigateToProductDetail = {
                    navController.navigate(
                        route = Route.ProductDetail(
                            isPreview = false
                        )
                    )
                }
            )
        }
        composable<Route.LikeList> {
            LikeItemListScreen(
                activityViewModel = activityViewModel,
                onNavigateToProductDetail = {
                    navController.navigate(
                        route = Route.ProductDetail(
                            isPreview = false
                        )
                    )
                }
            )
        }
        composable<Route.RecommendList> {
            RecommendItemScreen(
                activityViewModel = activityViewModel,
                onNavigateToProductDetail = {
                    navController.navigate(
                        route = Route.ProductDetail(
                            isPreview = false
                        )
                    )
                }

            )
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
                onNavigateToBabyDetail = { id ->
                    navController.navigate(
                        route = Route.BabyDetail(babyId = id)
                    )

                }
            )
        }

        composable<Route.BabyDetail> {
            BabyDetailScreen(
                viewModel = activityViewModel,
                babyId = it.arguments?.getInt("babyId") ?: 0
            )
        }

        // 추억 관련 화면들
        composable<Route.MemoryUpload> {
            MemoryUploadScreen(
                onNavigateToMemoryDetail = { memoryId, babyId ->
                    navController.navigate(
                        route = Route.MemoryDetail(memoryId = memoryId, babyId = babyId)
                    ) {
                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: "") {
                            inclusive = true
                        }
                    }
                },
                memoryId = it.arguments?.getInt("memoryId") ?: 0,
                babyId = it.arguments?.getInt("babyId") ?: 0,
            )
        }

        composable<Route.MemoryDetail> {
            MemoryDetailScreen(
                onNavigateToMemoryUpload = { memoryId, babyId ->
                    navController.navigate(
                        route = Route.MemoryUpload(memoryId = memoryId, babyId = babyId)
                    ) {
                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: "") {
                            inclusive = true
                        }
                    }
                },
                memoryId = it.arguments?.getInt("memoryId") ?: 0,
                babyId = it.arguments?.getInt("babyId") ?: 0,
            )
        }
        // 중고 거래 관련 화면들
        composable<Route.UploadProduct> {
            UploadProductScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Route.ProductDetail> { backStackEntry ->
            val isPreview = backStackEntry.toRoute<Route.ProductDetail>().isPreview
            ProductDetailScreen(
                activityViewModel = activityViewModel,
                isPreview = isPreview,
                onBackClick = { navController.popBackStack() },
                onNavigateToUpload = {
                    navController.navigate(
                        route = Route.UploadProduct(
                            isModify = it
                        )
                    )
                },
                onNavigateToChat = { navController.navigate(route = Route.Chat) },
                onNavigateToRecommend = { navController.navigate(route = Route.RecommendList) }
            )
        }

        // 채팅 관련 화면들
        composable<Route.Chat> {
            ChatScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

