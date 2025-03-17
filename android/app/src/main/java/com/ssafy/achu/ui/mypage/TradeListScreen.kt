package com.ssafy.achu.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White

@Composable
fun TradeListScreen() {

    val saleList = remember { mutableListOf<String>() }
    val purchaseList = remember { mutableListOf<String>() }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = White)
        .padding(horizontal = 24.dp)) {

        Column (){

        }
    }


}

@Preview
@Composable
fun TradeListScreenPreview() {
    AchuTheme {
        TradeListScreen()
    }
}
