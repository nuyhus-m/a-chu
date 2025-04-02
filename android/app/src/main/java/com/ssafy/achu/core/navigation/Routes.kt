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
    data class BabyDetail(val babyId: Int) : Route

    @Serializable
    data class MemoryDetail(val memoryId: Int, val babyId: Int) : Route

    @Serializable
    data class MemoryUpload(val memoryId: Int, val babyId: Int, val productName: String) : Route

    @Serializable
    data class ProductDetail(val isPreview: Boolean) : Route

    @Serializable
    data class UploadProduct(val isModify: Boolean) : Route

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