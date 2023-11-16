package app.junsu.onui_android.presentation.feature.store

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import app.junsu.onui_android.presentation.ui.theme.primaryContainer
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SunStoreScreen(navController: NavController) {
    val viewModel: SunStoreViewModel = viewModel()
    val sproutImages = listOf(
        R.drawable.sprout_good,
        R.drawable.sprout_fine,
        R.drawable.sprout_normal,
        R.drawable.sprout_green,
        R.drawable.sprout_bad,
    )
    val flushingImages = listOf(
        R.drawable.flushing_good,
        R.drawable.flushing_fine,
        R.drawable.flushing_normal,
        R.drawable.flushing_bad,
        R.drawable.flushing_very_bad,
    )

    val catImages = listOf(
        R.drawable.cat_good,
        R.drawable.cat_fine,
        R.drawable.cat_normal,
        R.drawable.cat_bad,
        R.drawable.cat_very_bad,
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(title = "햇님 방앗간", modifier = Modifier.clickable { navController.popBackStack() })
        Object(
            title = "새싹쓰",
            images = sproutImages,
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.changeTheme("새싹쓰")
                }
            },
        )
        Object(
            title = "홍조쓰",
            images = flushingImages,
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.changeTheme("홍조쓰")
                }
            },
        )
        Object(
            title = "애옹쓰",
            images = catImages,
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.changeTheme("애옹쓰")
                }
            },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Object(title: String, images: List<Int>, onClick: () -> Unit) {
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
                    .background(primaryContainer)
                    .align(Alignment.End)
            ) {
                Text(
                    text = "적용하기",
                    style = body2,
                    color = onPrimaryContainer,
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