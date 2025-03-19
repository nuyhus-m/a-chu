package com.ssafy.achu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ssafy.achu.core.components.BottomNavBar
import com.ssafy.achu.core.navigation.NavGraph
import com.ssafy.achu.core.theme.White

@Composable
fun AchuApp() {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        // Content
        NavGraph(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
                .padding(bottom = 80.dp)
        )

        // Bottom Bar
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomNavBar(navController)
        }
    }
}