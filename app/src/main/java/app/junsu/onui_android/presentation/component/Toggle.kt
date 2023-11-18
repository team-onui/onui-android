package app.junsu.onui_android.presentation.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.junsu.onui_android.presentation.ui.theme.body2

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    items: Array<String>,
    position: Int = 0,
    onSelectedItemChange: (position: Int) -> Unit = {}
) {
    val toggleItems = if (items.size > 4) items.sliceArray(0..3) else items
    BasicToggleButton(
        modifier = modifier,
        items = toggleItems,
        position = position,
        onSelectedItemChange = onSelectedItemChange
    )
}

@Composable
fun BasicToggleButton(
    modifier: Modifier = Modifier,
    items: Array<String>,
    position: Int = 0,
    onSelectedItemChange: (position: Int) -> Unit
) {
    var eachContentWidth by remember { mutableStateOf(0) }
    val eachContentDpWidth = LocalDensity.current.run { eachContentWidth.toDp() }
    var positionState by remember { mutableStateOf(position) }
    val emptySpaceWidth: Dp by animateDpAsState(
        ((eachContentDpWidth.value * positionState) + (8 * positionState)).dp,
        label = ""
    )
    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .height(44.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Color(0xFFD9D9D9))
            .padding(4.dp)
            .onSizeChanged {
                if (items.isNotEmpty())
                    eachContentWidth = (it.width - (8 * items.size)) / items.size
            }
    ) {
        Row {
            Spacer(modifier = Modifier.width(emptySpaceWidth))
            Spacer(
                modifier = Modifier
                    .width(eachContentDpWidth)
                    .height(36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
            )
        }
        Row {
            for (i in items.indices) {
                ToggleItem(
                    modifier = Modifier
                        .height(36.dp)
                        .width(eachContentDpWidth),
                    text = items[i],
                    onClick = {
                        positionState = i
                        onSelectedItemChange(i)
                    }
                )
                if (i != items.lastIndex) Spacer(modifier = Modifier.size(8.dp))
                if (i == 3) break
            }
        }
    }
}

@Composable
private fun ToggleItem(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = body2)
    }
}