package app.junsu.onui_android.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.junsu.onui_android.presentation.ui.theme.onSurface
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.title1

@Composable
fun Header(
    title: String,
    modifier: Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(surface)
            .fillMaxHeight(0.08f)
            .padding(start = 12.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "arrowBack",
                modifier = modifier,
                tint = onSurfaceVariant,
            )
            Text(
                text = title,
                style = title1,
                color = onSurface,
                modifier = Modifier.padding(start = 12.dp),
            )
        }
    }
}
