import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
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
import com.ssafy.achu.data.model.baby.BabyResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBabyDialog(
    babyList: List<BabyResponse>,
    onConfirm: (Int) -> Unit // 선택된 아기의 id를 반환하도록 수정
) {
    var selectedBaby by remember { mutableStateOf("아이를 선택해주세요.") }
    var selectedBabyId by remember { mutableStateOf<Int?>(null) } // 선택된 아기의 ID 저장
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 배경 어두운 오버레이 추가
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

                // 🔹 드롭다운 메뉴 추가
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .border(1.dp, FontPink, RoundedCornerShape(30.dp))
                            .clickable { expanded = true }
                            .padding(vertical = 12.dp, horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row {
                            Text(
                                text = selectedBaby,
                                style = AchuTheme.typography.regular16,
                                color = FontGray
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

                    // 드롭다운 메뉴
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        babyList.forEach { baby ->
                            DropdownMenuItem(
                                text = { Text(baby.nickname) },
                                onClick = {
                                    selectedBaby = baby.nickname
                                    selectedBabyId = baby.id // 선택된 아기의 ID 저장
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 버튼들
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
                                if (selectedBabyId != null) FontPink else DisabledPink,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = selectedBabyId != null) {
                                selectedBabyId?.let { onConfirm(it) }
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
