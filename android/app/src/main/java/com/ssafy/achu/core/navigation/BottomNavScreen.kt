package com.ssafy.achu.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssafy.achu.R

sealed class BottomNavScreen(
    val route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val icon: Int
) {

    data object Home : BottomNavScreen(
        route = HOME,
        titleResId = R.string.home,
        icon = R.drawable.ic_home
    )

    data object ProductList : BottomNavScreen(
        route = PRODUCT_LIST,
        titleResId = R.string.shopping,
        icon = R.drawable.ic_shopping
    )

    data object MemoryList : BottomNavScreen(
        route = MEMORY_LIST,
        titleResId = R.string.memory,
        icon = R.drawable.ic_memory
    )

    data object ChatList : BottomNavScreen(
        route = CHAT_LIST,
        titleResId = R.string.chat,
        icon = R.drawable.ic_chat
    )

    data object MyPage : BottomNavScreen(
        route = MY_PAGE,
        titleResId = R.string.mypage,
        icon = R.drawable.ic_mypage
    )

    companion object {
        const val HOME = "home"
        const val PRODUCT_LIST = "productlist"
        const val MEMORY_LIST = "memorylist"
        const val CHAT_LIST = "chatlist"
        const val MY_PAGE = "mypage"
        const val MY_TRADE_LIST = "tradelist"
    }
}

val bottomNavBarScreens =
    listOf(
        BottomNavScreen.Home,
        BottomNavScreen.ProductList,
        BottomNavScreen.MemoryList,
        BottomNavScreen.ChatList,
        BottomNavScreen.MyPage
    )