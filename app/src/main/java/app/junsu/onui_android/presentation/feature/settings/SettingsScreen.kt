package app.junsu.onui_android.presentation.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui_android.presentation.component.CustomSwitchButton
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.navigation.AppNavigationItem
import app.junsu.onui_android.presentation.ui.theme.body2
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.label
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.surface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel: SettingViewModel = viewModel()
    var abuseState: Boolean? by remember { mutableStateOf(null) }
    var alarmState by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
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