package app.junsu.onui_android.presentation.feature.store

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.title2

@Composable
fun MoonStoreScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(title = "달님 만물상", modifier = Modifier.clickable { navController.popBackStack() })
        Row(
            modifier = Modifier
                .background(gray3)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                text = "추후 구현 예정입니다 ^^",
                style = title2,
                textAlign = TextAlign.Center,
            )
        }
    }
}