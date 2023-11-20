package app.junsu.onui_android.presentation.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.junsu.onui_android.data.response.mission.MissionResponse
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.label
import app.junsu.onui_android.presentation.ui.theme.onPrimaryContainer
import app.junsu.onui_android.presentation.ui.theme.onSurface
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.primaryContainer
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun TaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel,
) {
    var colorState by remember { mutableStateOf(false) }
    var isFinishedState by remember { mutableStateOf(false) }
    LaunchedEffect(colorState) {
        taskViewModel.fetchTask()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(title = "과제", modifier = Modifier.clickable { navController.popBackStack() })
        Tasks(
            task = taskViewModel.task,
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    taskViewModel.finishMission(it)
                }
            },
            colorState = colorState,
            isFinishedState = isFinishedState,
            changeColorState = {
                colorState = it
                isFinishedState = it
            },
        )
    }
}

@Composable
fun Tasks(
    task: MissionResponse,
    onClick: (UUID) -> Unit,
    colorState: Boolean,
    isFinishedState: Boolean,
    changeColorState: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(surface)
    ) {
        for (i in 0..2) {
            changeColorState(task.missions[i].isFinished)
            val color = if (colorState) onPrimaryContainer else primaryContainer
            val isFinished = if (isFinishedState) primaryContainer else surface
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 10.dp,
                    )
            ) {
                Row(modifier = Modifier.padding(bottom = 4.dp)) {
                    Text(
                        text = task.missions[i].name,
                        style = title3,
                        color = onSurface,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = task.missions[i].goal,
                        style = title3,
                        color = onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "완료!",
                        style = title3,
                        color = isFinished,
                    )
                }
                Divider(
                    modifier = Modifier
                        .height(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    color = color,
                )
                Text(
                    text = task.missions[i].message,
                    style = label,
                    color = onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Box(
                modifier = Modifier
                    .padding(bottom = 12.dp, end = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(12.dp),
                        color = if (!task.missions[i].isFinished) primary else primaryContainer
                    )
                    .background(if (!task.missions[i].isFinished) surface else primaryContainer)
                    .align(Alignment.End)
            ) {
                Text(
                    text = if (!task.missions[i].isFinished) "완료됨" else "완료 하기",
                    style = body2,
                    color = if (!task.missions[i].isFinished) primary else onPrimaryContainer,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        )
                        .clickable { onClick(task.missions[i].id) }
                )
            }
        }
    }
    Text(
        text = "과제는 매일 새로 갱신되어요.",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = label,
        color = onSurfaceVariant
    )
}
