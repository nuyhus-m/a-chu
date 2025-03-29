package com.ssafy.achu.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssafy.achu.R

sealed class BottomNavScreen(
    val route: BottomNavRoute,
    @StringRes val titleResId: Int,
    @DrawableRes val icon: Int
) {

    data object Home : BottomNavScreen(
        route = BottomNavRoute.Home,
        titleResId = R.string.home,
        icon = R.drawable.ic_home
    )

    data object ProductList : BottomNavScreen(
        route = BottomNavRoute.ProductList,
        titleResId = R.string.shopping,
        icon = R.drawable.ic_shopping
    )

    data object MemoryList : BottomNavScreen(
        route = BottomNavRoute.MemoryList,
        titleResId = R.string.memory,
        icon = R.drawable.ic_memory
    )

    data object ChatList : BottomNavScreen(
        route = BottomNavRoute.ChatList,
        titleResId = R.string.chat,
        icon = R.drawable.ic_chat
    )

    data object MyPage : BottomNavScreen(
        route = BottomNavRoute.MyPage,
        titleResId = R.string.mypage,
        icon = R.drawable.ic_mypage
    )
}

val bottomNavScreens =
    listOf(
        BottomNavScreen.Home,
        BottomNavScreen.ProductList,
        BottomNavScreen.MemoryList,
        BottomNavScreen.ChatList,
        BottomNavScreen.MyPage
    )