package app.junsu.onui_android.presentation.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui_android.catImages
import app.junsu.onui_android.defaultImage
import app.junsu.onui_android.flushingImages
import app.junsu.onui_android.imageTheme
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.onPrimaryContainer
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.primaryContainer
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title2
import app.junsu.onui_android.sproutImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ThemeScreen(navController: NavController) {
    val viewModel: SettingViewModel = viewModel()
    val themes = arrayListOf("")
    for (i in 0..viewModel.themeList.themeList.size - 1) {
        themes.add(viewModel.themeList.themeList[i].theme)
    }
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getTheme()
            viewModel.fetchProfile()
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(title = "씨앗 저장소", modifier = Modifier.clickable { navController.popBackStack() })
        Object(
            title = "default",
            images = defaultImage,
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.changeTheme("새싹쓰")
                    imageTheme(0)
                    viewModel.fetchProfile()
                }
            },
            check = viewModel.profile.theme == "default"
        )
        if (viewModel.themeList.themeList.isNotEmpty()) {
            Object(
                title = "새싹쓰",
                images = sproutImages,
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.changeTheme("새싹쓰")
                        imageTheme(3)
                        viewModel.fetchProfile()
                    }
                },
                check = viewModel.profile.theme == "새싹쓰"
            )
            Object(
                title = "홍조쓰",
                images = flushingImages,
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.changeTheme("홍조쓰")
                        imageTheme(2)
                        viewModel.fetchProfile()
                    }
                },
                check = viewModel.profile.theme == "홍조쓰"
            )
            Object(
                title = "애옹쓰",
                images = catImages,
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.changeTheme("애옹쓰")
                        imageTheme(1)
                        viewModel.fetchProfile()
                    }
                },
                check = viewModel.profile.theme == "애옹쓰"
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Object(title: String, images: List<Int>, onClick: () -> Unit, check: Boolean) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        style = title2,
        textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(surface)
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                images.forEach {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = "object",
                        modifier = Modifier
                            .weight(3f)
                            .padding(top = 12.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .padding(bottom = 12.dp, end = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(12.dp),
                        color = if (check) primary else primaryContainer
                    )
                    .background(if (check) surface else primaryContainer)
                    .align(Alignment.End)
            ) {
                Text(
                    text = if (check) "적용됨" else "변경",
                    style = body2,
                    color = if (check) primary else onPrimaryContainer,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp,
                        )
                        .clickable { onClick() }
                )
            }
        }
    }
}