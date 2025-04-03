package com.ssafy.achu.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White

@Composable
fun BasicTopAppBar(title: String, onBackClick: () -> Unit, ableBack: Boolean = true) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 68.dp, bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (ableBack) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Text(
            text = title,
            style = AchuTheme.typography.semiBold24
        )
    }
}

@Composable
fun CenterTopAppBar(title: String, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 68.dp, bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            text = title,
            style = AchuTheme.typography.semiBold24
        )
    }
}

@Composable
fun TopBarWithMenu(
    title: String = "",
    onBackClick: () -> Unit,
    menuFirstText: String = "",
    menuSecondText: String = "",
    onMenuFirstItemClick: () -> Unit,
    onMenuSecondItemClick: () -> Unit,
    isMenuVisible: Boolean = true
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 68.dp, bottom = 24.dp)
            .background(White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = title,
                style = AchuTheme.typography.semiBold24,
                modifier = Modifier.weight(1f)
            )

            if (isMenuVisible) {


                Box {
                    IconButton(
                        onClick = { menuExpanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.more)
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        containerColor = White
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = menuFirstText,
                                    style = AchuTheme.typography.regular16
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                onMenuFirstItemClick()
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = menuSecondText,
                                    style = AchuTheme.typography.regular16
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                onMenuSecondItemClick()
                            }
                        )
                    }
                }
            }
        }
    }
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

@Preview(showBackground = true)
@Composable
fun TopBarWithMenuPreview() {
    AchuTheme {
        TopBarWithMenu(
            title = "거래상세",
            onBackClick = {},
            menuFirstText = "수정",
            menuSecondText = "삭제",
            onMenuFirstItemClick = {},
            onMenuSecondItemClick = {},
        )
    }
}

