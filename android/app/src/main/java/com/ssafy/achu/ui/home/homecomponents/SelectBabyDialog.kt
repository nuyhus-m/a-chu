import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.DisabledPink
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.baby.BabyResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBabyDialog(
    babyList: List<BabyResponse>,
    onConfirm: (BabyResponse) -> Unit
) {
    var selectedBabyString by remember { mutableStateOf("아이를 선택해주세요.") }
    var selectedBaby by remember { mutableStateOf<BabyResponse?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(30.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_smiling_face),
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "오늘은 어떤 자녀와 함께 할까요?",
                    style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp),
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.Black
                )

                Text(
                    "자녀를 선택해 주세요.",
                    style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp),
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color.Black
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    modifier = Modifier.background(White),
                    onExpandedChange = {
                        expanded = !expanded
                        focusManager.clearFocus()
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .border(1.dp, FontPink, RoundedCornerShape(30.dp))
                            .clickable { expanded = true }
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                            .focusRequester(focusRequester)
                            .focusable()
                            .menuAnchor(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Text(
                                text = selectedBabyString,
                                style = AchuTheme.typography.regular16,
                                color = if (selectedBaby != null) FontGray else FontBlack
                            )
                            Spacer(modifier = Modifier.weight(1.0f))

                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                colorFilter = ColorFilter.tint(FontBlack)
                            )
                        }
                    }

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(White)
                    ) {
                        babyList.forEach { baby ->
                            DropdownMenuItem(
                                modifier = Modifier.background(White),
                                text = {
                                    Text(
                                        baby.nickname,
                                        style = AchuTheme.typography.regular16
                                    )
                                },
                                onClick = {
                                    selectedBabyString = baby.nickname
                                    selectedBaby = baby
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .width(220.dp)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                    )

                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .background(
                                if (selectedBaby != null) FontPink else DisabledPink,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = selectedBaby != null) {
                                selectedBaby?.let { onConfirm(it) }
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "확인",
                            style = AchuTheme.typography.semiBold16White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BabyDialogPreview() {
    AchuTheme {
        SelectBabyDialog(
            babyList = listOf(
                BabyResponse("2020-05-15", "여", 1, "https://example.com/baby1.png", "아기1"),
                BabyResponse("2018-10-22", "남", 2, "https://example.com/baby2.png", "아기2"),
                BabyResponse("2022-01-30", "여", 3, "https://example.com/baby3.png", "아기3")
            ),
            onConfirm = {}
        )
    }
}
