package com.ssafy.achu.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.PointBlueLineBtn
import com.ssafy.achu.core.theme.AchuTheme

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = ProductListViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = modifier
                    .padding(horizontal = 24.dp)
            ) {
                SearchBar(
                    value = uiState.query,
                    onValueChange = { viewModel.updateQuery(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val items = listOf("All", "의류", "교구/완구", "잡화", "출산용", "버튼 6", "버튼 7", "버튼 8")
            HorizontalButtonList(
                items = items,
                onButtonClick = {}
            )
        }
    }
}

@Composable
private fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.searchbar_placeholder),
                style = AchuTheme.typography.regular16.copy(color = Color(0xFFC4C4C4))
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF3F3F3), shape = RoundedCornerShape(16.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        ),
    )
}

@Composable
fun HorizontalButtonList(
    items: List<String>,
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0
) {
    var selectedIndex by remember { mutableIntStateOf(initialSelectedIndex) }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.withIndex().toList()) { (index, item) ->
            val isSelected = index == selectedIndex

            PointBlueLineBtn(buttonText = item, isSelected = isSelected) {
                selectedIndex = index
                onButtonClick(item)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    AchuTheme {
        ProductListScreen()
    }
}