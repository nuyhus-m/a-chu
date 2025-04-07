import android.app.DatePickerDialog
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
fun CreateBabyDialog(
    onConfirm: () -> Unit // 날짜를 전달하도록 변경
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 배경 어두운 오버레이 추가
            .padding(32.dp).clickable (enabled = false){},
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
                        .width(60.dp)
                        .height(60.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = "A-Chu",
                        style = AchuTheme.typography.semiBold20.copy(lineHeight = 30.sp),
                        color = FontPink

                    )
                    Text(
                        text = "에 처음 가입하셨네요!",
                        style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp),

                        )
                }

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "상품추천, 추억등록을 위한\n아이를 등록해주세요!",
                    style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp),
                    color = Color.Black,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = "*미등록시 사용이 제한됩니다.",
                    style = AchuTheme.typography.semiBold12LightGray,
                    color = FontGray,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 버튼들
                Row(
                    modifier = Modifier
                        .width(220.dp)
                        ,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .padding(vertical = 12.dp),
                    )

                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .background(FontPink, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                onConfirm()
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


@Preview
@Composable
fun preview() {
    AchuTheme {
        CreateBabyDialog(

            onConfirm = { }
        )


    }
}
