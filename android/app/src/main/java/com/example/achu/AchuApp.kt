package com.example.achu

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.achu.core.components.BottomNavBar
import com.example.achu.core.navigation.NavGraph

@Composable
fun AchuApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavGraph(navController, Modifier.padding(innerPadding))
    }
}