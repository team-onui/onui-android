package app.junsu.onui_android.presentation.feature.calendar

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.junsu.onui.R
import app.junsu.onui_android.presentation.ui.theme.label
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title2
import app.junsu.onui_android.toSmallImage
import java.time.LocalDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Calendar(
    modifier: Modifier,
    onClick: (Boolean) -> Unit,
    fetchDate: (String) -> Unit,
) {
    val viewModel: CalendarViewModel = viewModel()
    val context = LocalContext.current
    var currentYear by remember { mutableIntStateOf(LocalDate.now().year) }
    var currentMonth by remember { mutableIntStateOf(LocalDate.now().monthValue) }
    val sharedPreferences = context.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
    val weekList = listOf("월", "화", "수", "목", "금", "토", "일")
    var fetchState by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchMonthDiary(sharedPreferences)
        fetchState = true
    }


    fun daysInMonth(month: Int, year: Int): Int {
        return when (month) {
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    val firstDayOfMonth = LocalDate.of(
        currentYear,
        currentMonth,
        1,
    )
    val dayOfWeek = firstDayOfMonth.dayOfWeek.value
    val daysInCurrentMonth = daysInMonth(currentMonth, currentYear)
    val dates: List<CalendarDate> = (1..daysInCurrentMonth).map { day ->
        CalendarDate(day, currentMonth, currentYear)
    }

    Column(modifier = modifier.fillMaxHeight(0.7f)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "keyboardArrowLeft",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        if (currentMonth - 1 < 1) {
                            currentYear--
                            currentMonth = 12
                        } else currentMonth--
                    }
            )
            Text(
                text = "${currentYear}년 ${currentMonth}월",
                style = title2,
                modifier = Modifier.padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "keyboardArrowRight",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        if (currentMonth + 1 > 12) {
                            currentYear++
                            currentMonth = 1
                        } else currentMonth++
                    }
            )
        }
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                )
                .clip(RoundedCornerShape(24.dp))
                .background(surface)
        ) {
            FlowRow(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                weekList.forEach { day ->
                    Text(
                        text = day,
                        style = label,
                        color = onSurfaceVariant,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (fetchState) {
                FlowRow(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                        )
                        .fillMaxWidth(),
                    maxItemsInEachRow = 7,
                ) {
                    var startOfMonth = 1
                    var select by remember { mutableStateOf("") }
                    val moodDays = arrayListOf<String>()
                    viewModel.mood.diaries.forEach {
                        moodDays.add(it.createdAt)
                    }
                    var moodDayCount = 0
                    dates.forEach { date ->
                        val today =
                            if (date.day < 10) "${date.year}-${date.month}-0${date.day}"
                            else "${date.year}-${date.month}-${date.day}"
                        if (dayOfWeek > startOfMonth) {
                            for (i in 2..dayOfWeek) {
                                Spacer(modifier = Modifier.weight(1f))
                                startOfMonth++
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .weight(1f)
                                .clickable {
                                    select = "${date.year}${date.month}${date.day}"
                                    onClick(true)
                                    fetchDate(select)
                                }
                        ) {
                            startOfMonth++
                            if (moodDays.isNotEmpty() && moodDayCount < moodDays.size && moodDays[moodDayCount] == today) {
                                Image(
                                    painter = painterResource(id = viewModel.mood.diaries[moodDayCount].mood.toSmallImage()),
                                    contentDescription = "borderIcon",
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                moodDayCount++
                            } else if (select == "${date.year}${date.month}${date.day}") {
                                Image(
                                    painter = painterResource(id = R.drawable.border_icon),
                                    contentDescription = "borderIcon",
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.default_icon),
                                    contentDescription = "defaultIcon",
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                            Text(
                                text = (date.day).toString(),
                                style = label,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = if (
                                    LocalDate.now().dayOfMonth == date.day &&
                                    LocalDate.now().monthValue == date.month &&
                                    LocalDate.now().year == date.year
                                ) primary else Color.Black,
                            )
                        }
                        if (startOfMonth == daysInCurrentMonth + dayOfWeek) {
                            for (i in 1..7) Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
