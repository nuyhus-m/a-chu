package com.ssafy.achu.core.navigation

import androidx.navigation.NavBackStackEntry
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// 직렬화: Route → String
fun Route.toRoute(): String {
    return Json.encodeToString(this)
}

// 역직렬화: String → Route
inline fun <reified T : Route> NavBackStackEntry.toRoute(): T {
    val json = this.arguments?.getString("route") ?: error("Route argument missing")
    return Json.decodeFromString(json)
}

@Serializable
sealed class Route {

    @Serializable
    data object TradeList : Route()

    @Serializable
    data object LikeList : Route()

    @Serializable
    data object RecommendList : Route()

    @Serializable
    data object UserInfo : Route()

    @Serializable
    data object BabyList : Route()

    @Serializable
    data class BabyDetail(val babyId: Int) : Route()

    @Serializable
    data class MemoryDetail(val memoryId: Int, val babyId: Int) : Route()

    @Serializable
    data class MemoryUpload(val memoryId: Int, val babyId: Int, val productName: String) : Route()

    @Serializable
    data class ProductDetail(val isPreview: Boolean) : Route()

    @Serializable
    data class UploadProduct(val isModify: Boolean) : Route()

    @Serializable
    data class Chat(val roomId: Int = -1) : Route()
}

@Serializable
sealed class BottomNavRoute : Route() {

    @Serializable
    data object Home : BottomNavRoute()

    @Serializable
    data class ProductList(val categoryId: Int) : BottomNavRoute()

    @Serializable
    data object MemoryList : BottomNavRoute()

    @Serializable
    data object ChatList : BottomNavRoute()

    @Serializable
    data object MyPage : BottomNavRoute()
}


@Serializable
sealed class AuthRoute : Route() {

    @Serializable
    data object SignIn : AuthRoute()

    @Serializable
    data object SignUp : AuthRoute()

    @Serializable
    data object FindAccount : AuthRoute()
}