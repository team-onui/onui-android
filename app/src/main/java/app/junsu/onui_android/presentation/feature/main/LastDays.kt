package app.junsu.onui_android.presentation.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.Mood
import app.junsu.onui_android.presentation.navigation.AppNavigationItem
import app.junsu.onui_android.presentation.ui.theme.onSurface
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title1
import app.junsu.onui_android.toBigImage
import app.junsu.onui_android.toSmallImage
import java.time.LocalDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LastDays(
    navController: NavController,
    weekViewModel: WeekViewModel,
) {
    val weekData = weekViewModel.weekData
    val weekDataReverse = weekData?.diaries?.reversed()
    val date = LocalDate.now()
    var today: String
    Column(
        modifier = Modifier
            .height(114.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable {
                navController.navigate(AppNavigationItem.Calendar.route)
            }
            .background(surface)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.last_days), style = title1, color = onSurface)
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "ArrowRight",
                modifier = Modifier.align(Alignment.CenterEnd),
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            var dayCheck = 0
            if (weekData?.diaries == null || weekData.diaries.isEmpty()) {
                for (i in 1..7) {
                    Icon(
                        painter = painterResource(id = Mood.WORST.toSmallImage()),
                        contentDescription = "moods",
                        modifier = Modifier
                            .fillMaxWidth(0.14f)
                            .padding(horizontal = 4.dp),
                        tint = Color(0xFFEEEEEE),
                    )
                }
            } else {
                for (i in 0..6) {
                    today =
                        if (date.dayOfMonth - 6 + i < 10) "${date.year}-${date.monthValue}-0${date.dayOfMonth - 6 + i}"
                        else "${date.year}-${date.monthValue}-${date.dayOfMonth - 6 + i}"
                    if (dayCheck < weekData.diaries.size && today == weekDataReverse!![dayCheck].created_at) {
                        Image(
                            painter = painterResource(id = weekDataReverse[dayCheck].mood.toBigImage()),
                            contentDescription = "moods",
                            modifier = Modifier
                                .fillMaxWidth(0.14f)
                                .padding(horizontal = 4.dp),
                        )
                        dayCheck += 1
                    } else {
                        Icon(
                            painter = painterResource(id = Mood.WORST.toSmallImage()),
                            contentDescription = "moods",
                            modifier = Modifier
                                .fillMaxWidth(0.14f)
                                .padding(horizontal = 4.dp),
                            tint = Color(0xFFEEEEEE),
                        )
                    }
                }
            }
        }
    }
}