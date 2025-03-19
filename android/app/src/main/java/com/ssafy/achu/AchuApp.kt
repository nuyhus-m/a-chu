package com.ssafy.achu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ssafy.achu.core.components.BottomNavBar
import com.ssafy.achu.core.navigation.NavGraph

private const val TAG = "AchuApp"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AchuApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        },
    ) { innerPadding ->
        NavGraph(navController, Modifier.padding(innerPadding))
    }
}