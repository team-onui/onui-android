package app.junsu.onui_android.presentation.feature.timeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.label
import app.junsu.onui_android.presentation.ui.theme.onSurface
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.outlineVariant
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title2
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun filterBadWords(input: String, badWords: List<String>): String {
    var filteredInput = input

    for (badWord in badWords) {
        val regex = Regex("\\b${badWord.replace(" ", "\\s+")}\\b", RegexOption.IGNORE_CASE)
        filteredInput = regex.replace(filteredInput, "*".repeat(badWord.length))
    }

    return filteredInput
}

@Composable
fun TimelineDetail(
    navController: NavController,
    viewModel: TimelineViewModel,
) {
    val lazyListState = rememberLazyListState()
    var text by remember { mutableStateOf("") }
    var update by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.fetchTimelineComment()
        delay(100)
        update = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(
            title = viewModel.timeline.content?.get(index = viewModel.index)?.writer ?: "ㅁㄴㅇ",
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Text(
            text = "${viewModel.fetchDate.monthValue}월 ${viewModel.fetchDate.dayOfMonth}일",
            style = title2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            textAlign = TextAlign.Center,
        )
        ContentDetail(
            title = viewModel.timeline.content!![viewModel.index].writer,
            content = viewModel.timeline.content!![viewModel.index].content,
            count = viewModel.timeline.content!![viewModel.index].commentCount,
            moods = viewModel.timeline.content!![viewModel.index].tagList,
            img = viewModel.timeline.content!![viewModel.index].image,
        )
        if (update) {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(horizontal = 12.dp),
                modifier = Modifier
                    .fillMaxHeight(0.83f)
                    .fillMaxWidth()
            ) {
                var commentCount = 0
                items(items = viewModel.timelineComment.orEmpty()) {
                    Chat(
                        text = viewModel.timelineComment?.get(commentCount)?.content.toString(),
                        theme = viewModel.timelineComment?.get(commentCount)?.userTheme.toString(),
                    )
                    commentCount++
                }
            }
        }
        val list = listOf(
            "fuck",
            "bitch",
            "shit",
            "damn",
            "piss",
            "pussy",
            "asshole",
            "sex",
            "fucking",
            "Fuck",
        )
        val filteredString = filterBadWords(text, list)

        Row(modifier = Modifier.background(surface)) {
            BasicTextField(
                value = if (list.contains(text)) filteredString else text,
                textStyle = body2,
                onValueChange = { text = it },
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp,
                    )
                    .weight(8f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(outlineVariant)
                    .padding(
                        vertical = 4.dp,
                        horizontal = 8.dp
                    )
                    .align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp, end = 16.dp)
                    .weight(1f)
                    .clickable {
                        CoroutineScope(Dispatchers.IO).launch {
                            update = false
                            viewModel.postComment(text = text)
                            viewModel.fetchTimelineComment()
                            text = ""
                            update = true
                        }
                    },
                tint = onSurfaceVariant,
            )
        }
    }
}

@Composable
fun Chat(text: String, theme: String) {
    Row(
        modifier = Modifier.padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            val color = if (theme.isNotBlank()) Color(("#$theme").toColorInt()) else primary
            Icon(
                painter = painterResource(id = R.drawable.chat_onui_body),
                contentDescription = "blank",
                tint = color,
                modifier = Modifier
                    .size(44.dp)
                    .align(Alignment.TopCenter)
            )
            Image(
                painter = painterResource(id = R.drawable.eye),
                contentDescription = "eye",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(6.dp)
                    .size(16.dp)
            )
        }
        Text(
            text = text,
            style = body2,
            color = onSurface,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(surface)
                .padding(6.dp)
        )
    }
}


@Composable
fun ContentDetail(
    title: String,
    content: String,
    count: Int,
    moods: List<String>,
    img: String?,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(24.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .background(surface)
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