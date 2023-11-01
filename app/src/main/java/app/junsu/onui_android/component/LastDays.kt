package app.junsu.onui_android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.junsu.onui.R
import app.junsu.onui_android.ui.theme.errorDarken2
import app.junsu.onui_android.ui.theme.onSurface
import app.junsu.onui_android.ui.theme.surface
import app.junsu.onui_android.ui.theme.title1

@Composable
fun LastDays() {
    Column(
        modifier = Modifier
            .height(140.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(surface)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.last_days), style = title1, color = onSurface)
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "ArrowRight",
                modifier = Modifier.align(Alignment.CenterEnd),
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            for (i in 0..5) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(color = errorDarken2)
                )
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}