package com.ssafy.achu.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ssafy.achu.R

sealed class BottomNavScreen(
    val route: BottomNavRoute,
    val routeName: String?,
    @StringRes val titleResId: Int,
    @DrawableRes val icon: Int
) {

    data object Home : BottomNavScreen(
        route = BottomNavRoute.Home,
        routeName = BottomNavRoute.Home::class.qualifiedName,
        titleResId = R.string.home,
        icon = R.drawable.ic_home
    )

    data object ProductList : BottomNavScreen(
        route = BottomNavRoute.ProductList(categoryId = 0),
        routeName = BottomNavRoute.ProductList::class.qualifiedName + "/{categoryId}",
        titleResId = R.string.shopping,
        icon = R.drawable.ic_shopping
    )

    data object MemoryList : BottomNavScreen(
        route = BottomNavRoute.MemoryList,
        routeName = BottomNavRoute.MemoryList::class.qualifiedName,
        titleResId = R.string.memory,
        icon = R.drawable.ic_memory
    )

    data object ChatList : BottomNavScreen(
        route = BottomNavRoute.ChatList,
        routeName = BottomNavRoute.ChatList::class.qualifiedName,
        titleResId = R.string.chat,
        icon = R.drawable.ic_chat
    )

    data object MyPage : BottomNavScreen(
        route = BottomNavRoute.MyPage,
        routeName = BottomNavRoute.MyPage::class.qualifiedName,
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

