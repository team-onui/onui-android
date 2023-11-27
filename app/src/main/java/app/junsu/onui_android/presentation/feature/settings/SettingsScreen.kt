package app.junsu.onui_android.presentation.feature.settings

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.presentation.component.CustomSwitchButton
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.navigation.AppNavigationItem
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.label
import app.junsu.onui_android.presentation.ui.theme.onPrimaryContainer
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.outline
import app.junsu.onui_android.presentation.ui.theme.outlineVariant
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.primaryContainer
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.smallImageList
import app.junsu.onui_android.toInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel: SettingViewModel = viewModel()
    var abuseState: Boolean? by remember { mutableStateOf(null) }
    var alarmState by remember { mutableStateOf(true) }
    val context = LocalContext.current
    var update by remember { mutableStateOf(true) }
    val sharedPreferences = context.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)

    LaunchedEffect(update) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.fetchProfile()
            abuseState = viewModel.profile.onFiltering
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(title = "설정", modifier = Modifier.clickable { navController.popBackStack() })
        Spacer(modifier = Modifier.height(12.dp))
        if (update) {
            MyPage(
                name = viewModel.profile.name,
                navController = navController,
                theme = viewModel.profile.profileTheme,
                viewModel = viewModel,
                updateChange = { update = it }
            )
            Object1(
                images = smallImageList[viewModel.profile.theme.toInt()],
                onClick = { navController.navigate(AppNavigationItem.Theme.route) },
            )
        }
        if (abuseState != null) {
            SwitchSetting(
                text = "비속어 필터",
                state = abuseState!!,
                onState = {
                    abuseState = it
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.changeFilter(it)
                    }
                },
                padding = 4,
            )
        }
        SwitchSetting(
            text = "알림",
            state = alarmState,
            onState = { alarmState = it },
            padding = 8,
        )
        Setting(
            text = "로그아웃",
            onClick = {
                sharedPreferences.edit().clear().apply()
                navController.navigate(AppNavigationItem.Login.route) { popUpTo(0) }
            },
        )
        Setting(
            text = "회원 탈퇴",
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.deleteUser(state = {
                        if (it) navController.navigate(AppNavigationItem.Login.route) { popUpTo(0) }
                    })
                }
            },
        )
        Text(
            text = "버전 정보: v1.0.0",
            style = label,
            color = onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SwitchSetting(
    text: String,
    state: Boolean,
    onState: (Boolean) -> Unit,
    padding: Int,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(surface)
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start,
        )
        CustomSwitchButton(
            switchPadding = padding.dp,
            buttonWidth = 52.dp,
            buttonHeight = 32.dp,
            value = state,
            state = onState,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun Setting(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(surface)
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPage(
    name: String,
    navController: NavController,
    theme: String,
    viewModel: SettingViewModel,
    updateChange: (Boolean) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(surface)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth(0.3f)
                .aspectRatio(1f)
                .background(gray3)
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                val color = if (theme.isNotBlank()) Color(("#$theme").toColorInt()) else primary
                Icon(
                    painter = painterResource(id = R.drawable.onui),
                    contentDescription = "blank",
                    tint = color,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                Image(
                    painter = painterResource(id = R.drawable.eyes),
                    contentDescription = "eyes",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(6.dp)
                        .size(16.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.pencel),
                contentDescription = "pencel",
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomEnd)
                    .clickable { navController.navigate(AppNavigationItem.Palette.route) },
            )
        }
        Row {
            BasicTextField(
                value = text,
                textStyle = body2,
                onValueChange = { text = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(outlineVariant)
            ) {
                TextFieldDefaults.TextFieldDecorationBox(
                    value = text,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = it,
                    singleLine = false,
                    enabled = false,
                    placeholder = { androidx.compose.material.Text(text = name) },
                    interactionSource = interactionSource,
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 12.dp)
                    .size(48.dp, 32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(color = outline, width = 1.dp, shape = RoundedCornerShape(8.dp))
                    .background(surface)
                    .clickable {
                        CoroutineScope(Dispatchers.IO).launch {
                            updateChange(false)
                            viewModel.patchName(text)
                            viewModel.fetchProfile()
                            text = ""
                            delay(50)
                            updateChange(true)
                        }
                    }
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "저장",
                    textAlign = TextAlign.Center,
                    style = label,
                    color = outline,
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Object1(
    images: List<Int>,
    onClick: () -> Unit,
) {
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
                    .background(primaryContainer)
                    .align(Alignment.End)
            ) {
                Text(
                    text = "변경",
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