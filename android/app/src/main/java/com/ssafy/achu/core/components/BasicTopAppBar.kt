package com.ssafy.achu.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ssafy.achu.core.theme.AchuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppBar(title: String = "", onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = AchuTheme.typography.semiBold20
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Preview
@Composable
fun BasicTopAppBarPreview() {
    AchuTheme {
        BasicTopAppBar(title = "거래상세", onBackClick = {})
    }
}

