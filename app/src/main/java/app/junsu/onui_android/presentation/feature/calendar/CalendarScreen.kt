package app.junsu.onui_android.presentation.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui_android.data.response.diary.DayDiaryResponse
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.label
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title2
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    navController: NavController,
) {
    val viewModel: CalendarViewModel = viewModel()
    val sheetState = rememberBottomSheetScaffoldState()
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var dayDiary: DayDiaryResponse? by remember { mutableStateOf(null) }
    var update by remember { mutableStateOf(false) }
    LaunchedEffect(date) {
        viewModel.fetchDiary(date = date, dayDiaryChange = { dayDiary = it })
        update = true
    }
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = sheetState,
        sheetContent = {
            if (update) {
                Content(
                    date = date,
                    content = dayDiary?.content ?: "기록이 없어요",
                    moods = dayDiary?.tagList ?: listOf(),
                    img = dayDiary?.image,
                )
            }
        },
        sheetPeekHeight = 240.dp,
        sheetContainerColor = surface,
        sheetSwipeEnabled = true,
    ) {
        Column(
            modifier = Modifier
                .background(gray3)
                .fillMaxHeight()
        ) {
            Header(
                title = "캘린더",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Calendar(
                modifier = Modifier,
                fetchDate = { date = it },
            )
        }
    }
}

@Composable
fun Content(
    date: String,
    content: String,
    moods: List<String>,
    img: String?,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(surface)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            text = date,
            style = title2,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        if (img != null) {
            AsyncImage(
                model = img,
                contentDescription = "img",
                modifier = Modifier
                    .padding(
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth()
                    .height(if (img.length > 1) 160.dp else 0.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 4.dp,
                ),
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 8.dp,
        ) {
            moods.forEach { mood ->
                Text(
                    text = mood,
                    color = primary,
                    style = label,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(
                            vertical = 4.dp,
                            horizontal = 8.dp,
                        ),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
            }
        }
        Text(
            text = content,
            style = body2,
            color = onSurfaceVariant,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp,
                )
        )
    }
}