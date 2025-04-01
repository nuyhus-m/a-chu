package com.ssafy.achu.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object TradeList : Route

    @Serializable
    data object LikeList : Route

    @Serializable
    data object RecommendList : Route

    @Serializable
    data object UserInfo : Route

    @Serializable
    data object BabyList : Route

    @Serializable
    data object BabyDetail : Route

    @Serializable
    data object MemoryDetail : Route

    @Serializable
    data object MemoryUpload : Route

    @Serializable
    data class ProductDetail(val productId: Int) : Route

    @Serializable
    data object UploadProduct : Route

    @Serializable
    data object Chat : Route

}

sealed interface BottomNavRoute : Route {

    @Serializable
    data object Home : BottomNavRoute

    @Serializable
    data class ProductList(val categoryId: Int) : BottomNavRoute

    @Serializable
    data object MemoryList : BottomNavRoute

    @Serializable
    data object ChatList : BottomNavRoute

    @Serializable
    data object MyPage : BottomNavRoute
}

sealed interface AuthRoute : Route {

    @Serializable
    data object SignIn : AuthRoute

    @Serializable
    data object SignUp : AuthRoute

    @Serializable
    data object FindAccount : AuthRoute
}