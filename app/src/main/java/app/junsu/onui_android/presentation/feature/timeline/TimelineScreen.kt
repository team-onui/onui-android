package app.junsu.onui_android.presentation.feature.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.navigation.AppNavigationItem
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.label
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title2
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun TimelineScreen(
    navController: NavController,
    viewModel: TimelineViewModel
) {
    val lazyListState = rememberLazyListState()
    var fetchDate by remember { mutableStateOf(viewModel.fetchDate) }
    var timeline by remember { mutableStateOf(viewModel.timeline) }
    LaunchedEffect(fetchDate) {
        viewModel.fetchTimeLine(
            idx = 0, size = 4, date = fetchDate.toString(),
            response = { timeline = it }
        )
        viewModel.fetchDate = fetchDate
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(
            title = "타임라인",
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "keyboardArrowLeft",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        fetchDate = fetchDate.minusDays(1)
                    },
            )
            Text(
                text = "${fetchDate.monthValue}월 ${fetchDate.dayOfMonth}일",
                style = title2,
                textAlign = TextAlign.Center,
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "keyboardArrowRight",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        fetchDate = fetchDate.plusDays(1)
                    },
            )
        }
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(horizontal = 12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(timeline.content?.size ?: 0) {
                Content(
                    title = timeline.content?.get(it)?.writer ?: "",
                    content = timeline.content?.get(it)?.content ?: "",
                    count = timeline.content?.get(it)?.commentCount ?: 0,
                    moods = timeline.content?.get(it)?.tagList ?: listOf(),
                    navController = navController,
                    img = timeline.content?.get(it)?.image,
                    index = it,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun Content(
    title: String,
    content: String,
    count: Int,
    moods: List<String>,
    navController: NavController,
    img: String?,
    index: Int,
    viewModel: TimelineViewModel,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(surface)
            .clickable {
                navController.navigate(AppNavigationItem.TimelineDetail.route)
                viewModel.index = index
            }
    ) {
        Text(
            text = title,
            style = body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp),
            textAlign = TextAlign.Center,
        )
        if (img != null) {
            AsyncImage(
                model = img,
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        bottom = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth()
                    .height(160.dp)
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
        Text(
            text = "댓글 ${count}개",
            color = onSurfaceVariant,
            style = label,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                ),
            textAlign = TextAlign.End
        )
    }
}