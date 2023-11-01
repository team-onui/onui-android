package app.junsu.onui_android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TaskScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .fillMaxSize(0.8f)
                .background(Color.White)
        )
    }
}
