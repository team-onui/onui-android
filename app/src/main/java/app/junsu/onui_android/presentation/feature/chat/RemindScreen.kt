package app.junsu.onui_android.presentation.feature.chat

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.Mood
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.getImageMultipart
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.label
import app.junsu.onui_android.presentation.ui.theme.onBackgroundVariant
import app.junsu.onui_android.presentation.ui.theme.onPrimaryContainer
import app.junsu.onui_android.presentation.ui.theme.onSurface
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.outline
import app.junsu.onui_android.presentation.ui.theme.outlineVariant
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.primaryContainer
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.toBigImage
import app.junsu.onui_android.toFile
import app.junsu.onui_android.toGrayImage
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RemindScreen(navController: NavController) {
    var icons = remember {
        mutableListOf(
            R.drawable.very_good_gray,
            R.drawable.good_gray,
            R.drawable.normal_gray,
            R.drawable.bad_gray,
            R.drawable.very_bad_gray,
        )
    }

    val selectMoods = arrayListOf<String>()
    var selectMood: Mood by remember { mutableStateOf(Mood.NOT_BAD) }
    var selectText by remember { mutableStateOf("") }
    val viewModel: RemindViewModel = viewModel()
    val context = LocalContext.current

    val selectedMood: MutableList<String> = remember { mutableStateListOf() }
    var viewState by remember { mutableIntStateOf(0) }
    var feelMessage by remember { mutableIntStateOf(0) }
    val feelChat = listOf(
        "기분이 좋으시다니 다행이에요☺️",
        "기분이 좋으시다니 다행이에요😊",
        "가벼운 산책은 어떤가요?",
        "기분이 좋아지시길 바래요\uD83E\uDD72",
        "기분이 좋아지시길 바래요\uD83E\uDD72",
    )
    var viewLate by remember { mutableIntStateOf(0) }
    var selectImage: String? by remember { mutableStateOf(null) }
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.fetchProfile()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(
            title = "기록하기",
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Chat(
                text = "안녕하세요~ 오늘은 어떤 하루였나요?",
                viewLate = { viewLate += it },
                theme = viewModel.profile.profileTheme,
            )
            if (viewLate >= 1) {
                TodayFeel(
                    icons = icons,
                    iconsChange = { icons = it },
                    viewStateChange = { if (viewState < 1) viewState = it },
                    message = { feelMessage = it },
                    fetchIcons = {
                        selectMood = it
                        Log.d("select", selectMood.toString())
                    }
                )
            }
            if (viewState > 0) {
                Column {
                    Chat(
                        text = "그렇군요, 오늘 하루 수고 많으셨어요.\n${feelChat[feelMessage]}\n어떤 감정을 느끼셨나요?",
                        viewLate = { viewLate += it },
                        theme = viewModel.profile.profileTheme,
                    )
                    if (viewLate >= 2) {
                        TodayMood(
                            selectedMoods = selectedMood,
                            selectedMoodChange = {
                                if (!selectedMood.contains(it)) selectedMood.add(it)
                            },
                            viewStateChange = { },
                            selectedMoodDelete = {
                                selectedMood.remove(it)
                            }
                        )
                        selectedMood.forEach { selectMoods.add(it) }
                    }
                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.fetchChat(selectedMood)
                                if (viewState < 2) viewState = 2
                            }
                        },
                        modifier = Modifier
                            .padding(top = 8.dp, end = 16.dp, start = 16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(primaryContainer),
                        colors = ButtonDefaults.buttonColors(primaryContainer)
                    ) {
                        Text(
                            text = "선택완료",
                            style = body2,
                            color = onPrimaryContainer,
                        )
                    }
                }
            }
            if (viewState > 1) {
                Chat(
                    text = "${viewModel.chatResponse.message}\n무슨 일이 있었는지 알려주세요.",
                    viewLate = { viewLate += it },
                    theme = viewModel.profile.profileTheme,
                )
                if (viewLate >= 3)
                    TodayNote(
                        viewStateChange = {
                            if (viewState < 3) viewState = it
                        },
                        fetchText = { selectText = it }
                    )
            }
            if (viewState > 2) {
                Chat(
                    text = "기억에 남는 사진이 있나요?",
                    viewLate = { viewLate += it },
                    theme = viewModel.profile.profileTheme,
                )
                if (viewLate >= 4) TodayPhoto(
                    viewStateChange = {
                        if (viewState < 4) viewState = it
                    },
                    fetchImage = {
                        CoroutineScope(Dispatchers.IO).launch {
                            kotlin.runCatching {
                                ApiProvider.diaryApi().fetchImg(
                                    getImageMultipart(
                                        "file",
                                        toFile(context = context, it)
                                    )
                                )
                            }.onSuccess { image ->
                                Log.d("it", image.url)
                                selectImage = image.url
                            }.onFailure { fail ->
                                Log.d("fail", fail.toString())
                            }
                        }
                    }
                )
            }
            if (viewState > 3) {
                Chat(
                    text = "다른 사람들과 감정을 공유해보실래요?",
                    viewLate = { viewLate += it },
                    theme = viewModel.profile.profileTheme,
                )
                if (viewLate >= 5) {
                    Row(modifier = Modifier.padding(bottom = 12.dp)) {
                        Button(
                            onClick = {
                                viewModel.postMood(selectText, selectMood, selectMoods, selectImage)
                                Log.d("viewModel", "$selectText,$selectMood")
                                navController.popBackStack()
                            },
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
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.postMood(
                                        selectText,
                                        selectMood,
                                        selectMoods,
                                        selectImage
                                    )
                                    Log.d("viewModel", "$selectText,$selectMood")
                                    delay(300)
                                    if (viewModel.id.isNotBlank()) viewModel.postTimeLine(viewModel.id)
                                }
                                navController.popBackStack()
                            },
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
    fetchIcons: (Mood) -> Unit,
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
            icons.forEachIndexed { index, _ ->
                val mood = when (index) {
                    0 -> Mood.GOOD
                    1 -> Mood.FINE
                    2 -> Mood.NOT_BAD
                    3 -> Mood.BAD
                    4 -> Mood.WORST
                    else -> Mood.NOT_BAD
                }
                Image(
                    painter = painterResource(
                        id = if (selectedIconIndex == index) mood.toBigImage()
                        else mood.toGrayImage()
                    ),
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
                                        mood.toBigImage()
                                    } else {
                                        R.drawable.very_good_gray
                                    }
                                }
                                iconsChange(icons)
                                viewStateChange(1)
                                fetchIcons(mood)
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
                val textColor = if (isSelected) surface else outline
                val color = if (isSelected) primary else surface
                Text(
                    text = mood,
                    color = textColor,
                    style = label,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(color)
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
    viewStateChange: (Int) -> Unit,
    fetchText: (String) -> Unit,
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
        onClick = {
            viewStateChange(3)
            fetchText(text)
        },
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
    viewStateChange: (Int) -> Unit,
    fetchImage: (Uri) -> Unit,
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        fetchImage(imageUri!!)
    }
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
    theme: String,
) {
    var showMessage by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(
                top = 12.dp,
                start = 8.dp,
                end = 16.dp,
            ),
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