package com.ssafy.achu.core.navigation

import HomeScreen
import MyPageScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.chat.chatdetail.ChatScreen
import com.ssafy.achu.ui.chat.chatlist.ChatListScreen
import com.ssafy.achu.ui.memory.MemoryListScreen
import com.ssafy.achu.ui.memory.memorydetail.MemoryDetailScreen
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
                onNavigateToLikeList = {
                    navController.navigate(route = Route.LikeList) {
                        launchSingleTop = true
                        restoreState = false

                    }
                },
                onNavigateToRecommend = {
                    navController.navigate(route = Route.RecommendList) {
                        launchSingleTop = true

                    }
                },
                onNavigateToBabyList = {
                    navController.navigate(route = Route.BabyList) {
                        launchSingleTop = true

                    }
                },
                onNavigateToProductList = { categoryId ->
                    navController.navigate(route = BottomNavRoute.ProductList(categoryId)) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true

                    }
                },
                onNavigateToProductDetail = {
                    navController.navigate(
                        route = Route.ProductDetail(
                            isPreview = false
                        )
                    ) {
                        launchSingleTop = true

                    }

                },
                onNavigateToBabyDetail = {
                    navController.navigate(
                        route = Route.BabyDetail(-1)
                    ) {
                        launchSingleTop = true
                        restoreState = true

                    }

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
                    ) {
                        launchSingleTop = true
                    }
                },
                onNavigateToProductDetail = {
                    navController.navigate(
                        route = Route.ProductDetail(
                            isPreview = false
                        )
                    ) {
                        launchSingleTop = true
                    }
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
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<BottomNavRoute.ChatList> {
            ChatListScreen(
                modifier = modifier,
                activityViewModel = activityViewModel,
                onNavigateToChat = { roomId ->
                    navController.navigate(
                        route = Route.Chat(roomId = roomId)
                    ) {
                        launchSingleTop = true
                    }
                }
            )

        }
        composable<BottomNavRoute.MyPage> {
            MyPageScreen(
                modifier = modifier,
                viewModel = activityViewModel,
                onNavigateToTradeList = {
                    navController.navigate(route = Route.TradeList) {
                        launchSingleTop = true
                    }
                },
                onNavigateToLikeList = {
                    navController.navigate(route = Route.LikeList) {
                        launchSingleTop = true
                    }
                },
                onNavigateToRecommend = {
                    navController.navigate(route = Route.RecommendList) {
                        launchSingleTop = true
                    }
                },
                onNavigateToBabyList = {
                    navController.navigate(route = Route.BabyList) {
                        launchSingleTop = true
                    }
                },
                onNavigateToUserInfo = {
                    navController.navigate(route = Route.UserInfo) {
                        launchSingleTop = true
                    }
                })
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
                    ) {
                        launchSingleTop = true
                    }
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
                    ) {
                        launchSingleTop = true
                    }
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
                    ) {
                        launchSingleTop = true
                    }
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
                    ) {
                        launchSingleTop = true
                    }

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
                productName = it.arguments?.getString("productName") ?: ""
            )
        }

        composable<Route.MemoryDetail> {
            MemoryDetailScreen(
                onNavigateToMemoryUpload = { memoryId, babyId ->
                    navController.navigate(
                        route = Route.MemoryUpload(
                            memoryId = memoryId,
                            babyId = babyId,
                            productName = ""
                        )
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
            val isModify = it.toRoute<Route.UploadProduct>().isModify
            UploadProductScreen(
                activityViewModel = activityViewModel,
                isModify = isModify,
                onBackClick = { navController.popBackStack() },
                onNavigateToDetail = {
                    navController.navigate(
                        route = Route.ProductDetail(
                            isPreview = true
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
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
                            isModify = true
                        )
                    ) {
                        launchSingleTop = true
                    }
                },
                onNavigateToChat = {
                    navController.navigate(route = Route.Chat())
                },
                onNavigateToRecommend = { navController.navigate(route = Route.RecommendList) },
                onNavigateToMemoryUpload = { babyId, productName ->
                    navController.navigate(
                        route = Route.MemoryUpload(
                            memoryId = 0,
                            babyId = babyId,
                            productName = productName
                        )
                    ) {
                        popUpTo(BottomNavRoute.ProductList(0))

                    }
                },
                onNavigateToProductList = {
                    navController.navigate(route = BottomNavRoute.ProductList(0)) {
                        popUpTo(BottomNavRoute.ProductList(0)) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // 채팅 관련 화면들
        composable<Route.Chat> {
            val roomId = it.toRoute<Route.Chat>().roomId
            ChatScreen(
                activityViewModel = activityViewModel,
                roomId = roomId,
                onBackClick = { navController.popBackStack() },

                )
        }
    }
}

