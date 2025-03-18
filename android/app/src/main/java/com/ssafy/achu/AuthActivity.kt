package com.ssafy.achu

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.ssafy.achu.core.navigation.AuthNavGraph
import com.ssafy.achu.core.theme.AchuTheme

class AuthActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AchuTheme {
                val navController = rememberNavController()
                AuthNavGraph(navController)
            }
        }
    }
}