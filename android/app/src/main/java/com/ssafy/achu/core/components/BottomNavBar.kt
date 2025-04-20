package com.ssafy.achu.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ssafy.achu.core.navigation.BottomNavRoute
import com.ssafy.achu.core.navigation.BottomNavScreen
import com.ssafy.achu.core.navigation.bottomNavScreens
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.ui.ActivityViewModel

@Composable
fun BottomNavBar(navController: NavHostController, viewModel: ActivityViewModel) {
    val unreadCount by viewModel.unreadCount.collectAsState()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf {
            currentBackStack?.destination?.route ?: BottomNavRoute.Home::class.qualifiedName
        }
    }

    val endPadding = when {
        unreadCount > 300 -> 12
        unreadCount > 99 -> 16
        unreadCount > 9 -> 20
        unreadCount > 0 -> 24
        else -> 0
    }

    AnimatedVisibility(
        visible = bottomNavScreens.map { it.routeName }
            .contains(currentRoute) && viewModel.isBottomNavVisible.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) {
                    WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 80.dp
                })
                .shadow(elevation = 8.dp, shape = RectangleShape)
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                bottomNavScreens.forEach { screen ->
                    val selected = currentRoute == screen.routeName
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = PointBlue),
                            ) {
                                // ✅ 현재 선택된 탭이 아닐 경우에만 navigate
                                if (!selected) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Icon(
                                painter = painterResource(screen.icon),
                                contentDescription = null,
                                tint = if (selected) PointBlue else FontGray
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = stringResource(screen.titleResId),
                                color = if (selected) PointBlue else FontGray,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        if (screen == BottomNavScreen.ChatList && unreadCount >= 1) {
                            val unreadCountStr =
                                if (unreadCount > 300) "300+" else unreadCount.toString()
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 8.dp, end = endPadding.dp)
                            ) {
                                Text(
                                    text = unreadCountStr,
                                    style = AchuTheme.typography.semiBold16.copy(color = White),
                                    modifier = Modifier
                                        .background(color = FontPink, shape = CircleShape)
                                        .padding(horizontal = 5.dp, vertical = 3.dp),
                                    fontSize = 8.sp,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ChatBottomBarItem(icon: Int, selected: Boolean, unreadCount: Int) {
    Box {
        Box(
            modifier = Modifier.padding(top = 2.dp, end = 4.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = if (selected) PointBlue else FontGray
            )
        }
        if (unreadCount >= 1) {
            val unreadCountStr = if (unreadCount >= 300) "300+" else unreadCount.toString()
            Box(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(
                    text = unreadCountStr,
                    style = AchuTheme.typography.semiBold16.copy(color = White),
                    modifier = Modifier
                        .background(color = FontPink, shape = CircleShape)
                        .padding(horizontal = 5.dp, vertical = 3.dp),
                    fontSize = 5.sp,
                    maxLines = 1,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBottomBarItemPreview() {
    AchuTheme {
        ChatBottomBarItem(selected = true, icon = BottomNavScreen.ChatList.icon, unreadCount = 300)
    }
}

@Preview
@Composable
fun CustomBottomNavBarPreview() {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .shadow(elevation = 8.dp, shape = RectangleShape)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                bottomNavScreens.forEach { screen ->
                    val selected = false

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = PointBlue)
                            ) {
                            }
                    ) {
                        Icon(
                            painter = painterResource(screen.icon),
                            contentDescription = null,
                            tint = if (selected) PointBlue else FontGray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(screen.titleResId),
                            color = if (selected) PointBlue else FontGray,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
