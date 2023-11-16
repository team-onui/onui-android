package app.junsu.onui_android.presentation.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.junsu.onui_android.presentation.ui.theme.title1

@Composable
fun TaskScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(24.dp))
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .background(Color.White)
        ) {
            Text(
                text = "태스크",
                style = title1,
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 10.dp
                    )
            )
            Box(
                modifier = Modifier
                    .padding(
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(0xFFD9D9D9))
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .aspectRatio(1f)
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .background(Color(0xFFD9D9D9))
            )
        }
    }
}
