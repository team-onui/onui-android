package app.junsu.onui_android.presentation.feature.store

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.onPrimaryContainer
import app.junsu.onui_android.presentation.ui.theme.onSurface
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.primaryContainer
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title2
import app.junsu.onui_android.sproutImages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SunStoreScreen(navController: NavController) {
    val viewModel: SunStoreViewModel = viewModel()

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.fetchRice()
            viewModel.fetchAllTheme()
        }
    }
    val themes: MutableList<String> = remember { mutableStateListOf() }
    Log.d("themes", themes.toString())
    for (i in 0..viewModel.themeList.themeList.size - 1) {
        if (!viewModel.themeList.themeList[i].isBought) {
            themes.add(viewModel.themeList.themeList[i].theme)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(title = "햇님 방앗간", modifier = Modifier.clickable { navController.popBackStack() })
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
                contentDescription = "blank",
                modifier = Modifier
                    .size(44.dp)
                    .padding(8.dp)
            )
            Text(
                text = "남은 쌀은 ${viewModel.rice}개 에요",
                style = body2,
                color = onSurface,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(surface)
                    .padding(6.dp)
            )
        }
        if (viewModel.themeList.themeList.isNotEmpty()) {
            Log.d("theme", "${themes},${viewModel.themeList}")
            viewModel.themeList.themeList.forEachIndexed { index, s ->
                Object(
                    title = s.theme,
                    images = sproutImages,
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.buyTheme(s.theme)
                            viewModel.fetchRice()
                        }
                    },
                    state = themes.contains(s.theme),
                    price = viewModel.themeList.themeList[index].price.toString()
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Object(
    title: String,
    images: List<Int>,
    onClick: () -> Unit,
    state: Boolean,
    price: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = title2,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
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
                        color = if (!state) primary else primaryContainer
                    )
                    .background(if (!state) surface else primaryContainer)
                    .align(Alignment.End)
            ) {
                Text(
                    text = if (!state) "구매완료" else price,
                    style = body2,
                    color = if (!state) primary else onPrimaryContainer,
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