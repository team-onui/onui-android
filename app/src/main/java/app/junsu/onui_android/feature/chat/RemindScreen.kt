package app.junsu.onui_android.feature.chat

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.junsu.onui.R
import app.junsu.onui_android.component.Header
import app.junsu.onui_android.ui.theme.body2
import app.junsu.onui_android.ui.theme.gray3
import app.junsu.onui_android.ui.theme.label
import app.junsu.onui_android.ui.theme.onBackgroundVariant
import app.junsu.onui_android.ui.theme.onPrimaryContainer
import app.junsu.onui_android.ui.theme.onSurface
import app.junsu.onui_android.ui.theme.onSurfaceVariant
import app.junsu.onui_android.ui.theme.outline
import app.junsu.onui_android.ui.theme.outlineVariant
import app.junsu.onui_android.ui.theme.primary
import app.junsu.onui_android.ui.theme.primaryContainer
import app.junsu.onui_android.ui.theme.surface
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.delay

@Composable
fun RemindScreen() {
    var icons = remember {
        mutableListOf(
            R.drawable.very_good_gray,
            R.drawable.good_gray,
            R.drawable.normal_gray,
            R.drawable.bad_gray,
            R.drawable.very_bad_gray,
        )
    }
    val selectedMood: MutableList<String> = remember { mutableStateListOf() }
    var viewState by remember { mutableIntStateOf(0) }
    var feelMessage by remember { mutableIntStateOf(0) }
    val feelChat = listOf("a", "b", "c", "d", "e")
    val moodChat = mapOf(
        "행복해요" to "as", "편안해요" to "bs", "신나요" to "cs",
        "자랑스러워요" to "ds", "희망차요" to "es"
    )
    var viewLate by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(title = "기록하기")
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Chat(text = "안녕하세요~ 오늘은 어떤 하루였나요?", viewLate = { viewLate += it })
            if (viewLate >= 1) {
                TodayFeel(
                    icons = icons,
                    iconsChange = { icons = it },
                    viewStateChange = { if (viewState < 1) viewState = it },
                    message = { feelMessage = it },
                )
            }
            if (viewState > 0) {
                Chat(
                    text = "그렇군요, 오늘 하루 수고 많으셨어요.\n${feelChat[feelMessage]}\n어떤 감정을 느끼셨나요?",
                    viewLate = { viewLate += it },
                )
                if (viewLate >= 2) {
                    TodayMood(
                        selectedMoods = selectedMood,
                        selectedMoodChange = {
                            if (!selectedMood.contains(it)) selectedMood.add(it)
                        },
                        viewStateChange = { if (viewState < 2) viewState = it },
                        selectedMoodDelete = {
                            selectedMood.remove(it)
                        }
                    )
                }
            }
            if (viewState > 1) {
                Chat(
                    text = "무슨 일이 있었는지 알려주세요.",
                    viewLate = { viewLate += it },
                )
                if (viewLate >= 3) TodayNote(viewStateChange = {
                    if (viewState < 3) viewState = it
                })
            }
            if (viewState > 2) {
                Chat(
                    text = "(격려 메시지)\n" + "기억에 남는 사진이 있나요?",
                    viewLate = { viewLate += it },
                )
                if (viewLate >= 4) TodayPhoto(viewStateChange = {
                    if (viewState < 4) viewState = it
                })
            }
            if (viewState > 3) {
                Chat(
                    text = "다른 사람들과 감정을 공유해보실래요?",
                    viewLate = { viewLate += it },
                )
                if (viewLate >= 5) {
                    Row(modifier = Modifier.padding(bottom = 12.dp)) {
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    top = 8.dp,
                                    end = 8.dp,
                                )
                                .weight(1f)
                                .clip(RoundedCornerShape(24.dp))
                                .background(gray3)
                                .border(
                                    width = 1.dp,
                                    color = onBackgroundVariant,
                                    shape = RoundedCornerShape(24.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(gray3)
                        ) {
                            Text(
                                text = "건너뛰기",
                                style = body2,
                                color = onBackgroundVariant,
                            )
                        }
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .padding(
                                    top = 8.dp,
                                    end = 16.dp,
                                )
                                .weight(1f)
                                .clip(RoundedCornerShape(24.dp))
                                .background(primaryContainer),
                            colors = ButtonDefaults.buttonColors(primaryContainer)
                        ) {
                            Text(
                                text = "공유하기",
                                style = body2,
                                color = onPrimaryContainer,
                            )
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(viewLate) { scrollState.animateScrollTo(scrollState.maxValue) }
}

@Composable
fun TodayFeel(
    icons: MutableList<Int>,
    iconsChange: (MutableList<Int>) -> Unit,
    viewStateChange: (Int) -> Unit,
    message: (Int) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var selectedIconIndex by remember { mutableIntStateOf(-1) }

    TodayState(text = "오늘은 어떤 하루였나요?") {
        Row(
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            )
        ) {
            icons.forEachIndexed { index, icon ->
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = when (index) {
                        0 -> "veryGood"
                        1 -> "good"
                        2 -> "normal"
                        3 -> "bad"
                        4 -> "veryBad"
                        else -> "icon"
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                        ) {
                            if (selectedIconIndex != index) {
                                selectedIconIndex = index
                                icons.forEachIndexed { i, _ ->
                                    icons[i] = if (i == index) {
                                        when (i) {
                                            0 -> R.drawable.very_good
                                            1 -> R.drawable.good
                                            2 -> R.drawable.normal
                                            3 -> R.drawable.bad
                                            4 -> R.drawable.very_bad
                                            else -> R.drawable.very_good
                                        }
                                    } else {
                                        when (i) {
                                            0 -> R.drawable.very_good_gray
                                            1 -> R.drawable.good_gray
                                            2 -> R.drawable.normal_gray
                                            3 -> R.drawable.bad_gray
                                            4 -> R.drawable.very_bad_gray
                                            else -> R.drawable.very_good_gray
                                        }
                                    }
                                }
                                iconsChange(icons)
                                viewStateChange(1)
                                message(index)
                            }
                        },
                )
            }
        }
    }
}

@Composable
fun TodayMood(
    selectedMoods: List<String>,
    selectedMoodChange: (String) -> Unit,
    selectedMoodDelete: (String) -> Unit,
    viewStateChange: (Int) -> Unit,
) {
    val moods = listOf(
        "행복해요", "편안해요", "신나요", "자랑스러워요", "희망차요",
        "열정적이에요", "설레요", "새로워요", "우울해요", "외로워요",
        "불안해요", "슬퍼요", "화나요", "부담돼요", "짜증나요", "피곤해요",
    )
    TodayState(text = "오늘 어떤 감정을 느끼셨나요?") {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 12.dp,
                ),
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 8.dp,
        ) {
            moods.forEach { mood ->
                val isSelected = selectedMoods.contains(mood)
                val textColor = if (isSelected) primary else outline
                Text(
                    text = mood,
                    color = textColor,
                    style = label,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            if (selectedMoods.contains(mood)) selectedMoodDelete(mood)
                            else selectedMoodChange(mood)
                            viewStateChange(2)
                        }
                        .border(
                            width = 1.dp,
                            color = textColor,
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
    }
}


@Composable
fun TodayNote(
    viewStateChange: (Int) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val textColor = if (text.isEmpty()) onBackgroundVariant else onPrimaryContainer
    val color = if (text.isEmpty()) onBackgroundVariant else primaryContainer
    TodayState(text = "무슨 일이 있었나요?") {
        BasicTextField(
            value = text,
            textStyle = body2,
            onValueChange = { text = it },
            modifier = Modifier
                .padding(
                    bottom = 12.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(outlineVariant)
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                )
        )
    }
    Button(
        onClick = { viewStateChange(3) },
        modifier = Modifier
            .padding(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (color == primaryContainer) primaryContainer else gray3
            )
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(24.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            if (color == primaryContainer) primaryContainer else gray3
        )
    ) {
        Text(
            text = if (textColor == onBackgroundVariant) "건너뛰기" else "작성완료",
            style = body2,
            color = textColor
        )
    }
}

@Composable
fun TodayPhoto(
    viewStateChange: (Int) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }
    val textColor = if (imageUri == null) onBackgroundVariant else onPrimaryContainer
    val color = if (imageUri == null) onBackgroundVariant else primaryContainer
    TodayState(text = "사진을 선택해주세요") {
        Box(
            modifier = Modifier
                .padding(
                    bottom = 12.dp,
                    start = 16.dp,
                    end = 16.dp,
                )
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(gray3)
                .clickable { launcher.launch("image/*") }
        ) {
            if (imageUri != null) {
                imageUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap.value = MediaStore.Images
                            .Media.getBitmap(context.contentResolver, it)

                    } else {
                        val source = ImageDecoder
                            .createSource(context.contentResolver, it)
                        bitmap.value = ImageDecoder.decodeBitmap(source)
                    }

                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            } else {
                Image(
                    painter = painterResource(id = R.drawable.photo),
                    contentDescription = "photo",
                    modifier = Modifier
                        .padding(
                            horizontal = 84.dp,
                            vertical = 70.dp
                        )
                        .align(Alignment.Center),
                )
            }
        }
    }
    Button(
        onClick = { viewStateChange(4) },
        modifier = Modifier
            .padding(
                top = 8.dp,
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                if (color == primaryContainer) primaryContainer else gray3
            )
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(24.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            if (color == primaryContainer) primaryContainer else gray3
        )
    ) {
        Text(
            text = if (textColor == onBackgroundVariant) "건너뛰기" else "선택완료",
            style = body2,
            color = textColor
        )
    }
}


@Composable
fun TodayState(
    text: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                top = 12.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(surface)
    ) {
        Text(
            text = text,
            style = label,
            color = onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp, bottom = 8.dp),
        )
        content()
    }
}

@Composable
fun Chat(
    text: String,
    viewLate: (Int) -> Unit,
) {
    var showMessage by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(
                top = 12.dp,
                start = 8.dp,
                end = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.chat_icon),
            contentDescription = "chatIcon",
            modifier = Modifier.padding(end = 8.dp)
        )
        if (!showMessage) MessageLoading()
        LaunchedEffect(Unit) {
            delay(500L)
            showMessage = true
            viewLate(1)
        }
        if (showMessage) {
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
}


@Composable
fun MessageLoading() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .size(56.dp, 34.dp)
            .background(surface)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(36.dp))
                .size(8.dp)
                .background(onSurfaceVariant)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(36.dp))
                .size(8.dp)
                .background(onSurface)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(36.dp))
                .size(8.dp)
                .background(onSurfaceVariant)
        )
    }
}