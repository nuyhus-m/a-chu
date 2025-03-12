package com.ssafy.achu.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.ssafy.achu.R

sealed class BottomNavScreen(val route: String, @StringRes val titleResId: Int, val icon: ImageVector) {

    data object Home : BottomNavScreen(
        route = HOME,
        titleResId = R.string.home,
        icon = Icons.Default.Home
    )

    data object ProductList : BottomNavScreen(
        route = PRODUCT_LIST,
        titleResId = R.string.shopping,
        icon = Icons.Default.Home
    )

    data object MemoryList : BottomNavScreen(
        route = MEMORY_LIST,
        titleResId = R.string.memory,
        icon = Icons.Default.Home
    )

    data object ChatList : BottomNavScreen(
        route = CHAT_LIST,
        titleResId = R.string.chat,
        icon = Icons.Default.Home
    )

    data object MyPage : BottomNavScreen(
        route = MY_PAGE,
        titleResId = R.string.mypage,
        icon = Icons.Default.Home
    )

    companion object {
        const val HOME = "home"
        const val PRODUCT_LIST = "productlist"
        const val MEMORY_LIST = "memorylist"
        const val CHAT_LIST = "chatlist"
        const val MY_PAGE = "mypage"
    }
}

val bottomNavBarScreens =
    listOf(BottomNavScreen.Home, BottomNavScreen.ProductList, BottomNavScreen.MemoryList, BottomNavScreen.ChatList, BottomNavScreen.MyPage)