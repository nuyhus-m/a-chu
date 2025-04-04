package com.ssafy.achu.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
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
import com.ssafy.achu.core.navigation.bottomNavScreens
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.ui.ActivityViewModel

private const val TAG = "BottomNavBar"

@Composable
fun BottomNavBar(navController: NavHostController, viewModel: ActivityViewModel) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    // 현재 화면 루트
    val currentRoute by remember {
        derivedStateOf {
            currentBackStack?.destination?.route ?: BottomNavRoute.Home::class.qualifiedName
        }
    }

    AnimatedVisibility(
        visible = bottomNavScreens.map { it.routeName }.contains(currentRoute)&& viewModel.isBottomNavVisible.value,
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

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(color = PointBlue),
                            ) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
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
