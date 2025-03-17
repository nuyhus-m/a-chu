package com.ssafy.achu.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopAppBar(title: String = "", onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = AchuTheme.typography.bold24
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        modifier = Modifier.padding(vertical = 24.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTopAppBar(title: String = "", onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = AchuTheme.typography.bold24
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(Icons.Default.Close, contentDescription = "Back")
            }
        },
        modifier = Modifier.padding(vertical = 24.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Preview(showBackground = true)
@Composable
fun BasicTopAppBarPreview() {
    AchuTheme {
        BasicTopAppBar(title = "거래상세", onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CenterTopAppBarPreview() {
    AchuTheme {
        CenterTopAppBar(title = "추억업로드", onBackClick = {})
    }
}

