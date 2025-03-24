import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import java.util.*

@Composable
fun SelectBabyBirthDialog(
    babyName: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit // 날짜를 전달하도록 변경
) {
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("출산일을 선택해주세요.") }

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedMonth = String.format("%02d", month + 1) // 월을 두 자리로 변환
                val formattedDay = String.format("%02d", dayOfMonth) // 일을 두 자리로 변환
                selectedDate = "$year.$formattedMonth.$formattedDay" // 날짜 형식 지정
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 배경 어두운 오버레이 추가
            .padding(32.dp), // 다이얼로그 주변 여백
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(30.dp))
                .padding(24.dp) // 다이얼로그 내부 여백
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 중앙 정렬
                verticalArrangement = Arrangement.Center // 수직 중앙 정렬
            ) {
                Image(
                    painter = painterResource(id = com.ssafy.achu.R.drawable.img_smiling_face),
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "${babyName}",
                        style = AchuTheme.typography.semiBold20.copy(lineHeight = 30.sp),
                        color = FontPink, modifier = Modifier.padding(start = 8.dp)
                    )

                    Text(
                        text = "님의",
                        style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp),

                        )
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = " 출산예정일이 지났어요!",
                    style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp),
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.Black
                )

                Text(
                    text = "태어났다면 생일을 입력해 주세요.",
                    style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp),
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color.Black
                )

                // 🔹 날짜 입력 필드
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .border(1.dp, FontPink, RoundedCornerShape(30.dp))
                        .clickable { showDatePicker() }
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row {
                        Text(
                            text = selectedDate, style = AchuTheme.typography.regular18,
                            color = if (selectedDate == "출산일을 선택해주세요.") FontGray else Color.Black
                        )
                        Spacer(modifier = Modifier.weight(1.0f))

                        Image(
                            painter = painterResource(id = com.ssafy.achu.R.drawable.ic_calendar),
                            contentDescription = null,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            colorFilter = ColorFilter.tint(FontGray)

                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "*마이페이지->우리아이 정보관리에서\n" +
                            "언제든 수정 가능합니다.", style = AchuTheme.typography.regular16,
                    color = FontGray,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

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
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .clickable(onClick = onDismiss)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "취소",
                            style = AchuTheme.typography.semiBold16,
                            color = Color.Gray
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .background(FontPink, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                onConfirm(selectedDate)
                                onDismiss()
                            } // 날짜 전달
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





@Preview
@Composable
fun PasswordUpdateDialogPreview() {
    AchuTheme {
        SelectBabyBirthDialog(
            "튼튼이",
            onDismiss = { /* 취소 클릭 시 동작 */ },
            onConfirm = { birthDate -> /* 확인 클릭 시 동작 */ }
        )


    }
}
