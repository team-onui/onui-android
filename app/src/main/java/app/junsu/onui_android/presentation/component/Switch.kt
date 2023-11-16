package app.junsu.onui_android.presentation.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.junsu.onui_android.presentation.ui.theme.outline
import app.junsu.onui_android.presentation.ui.theme.primary

@Composable
fun CustomSwitchButton(
    switchPadding: Dp,
    buttonWidth: Dp,
    buttonHeight: Dp,
    value: Boolean,
    state: (Boolean) -> Unit,
) {

    val switchSize by remember {
        mutableStateOf(buttonHeight - switchPadding * 2)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var switchClicked by remember {
        mutableStateOf(value)
    }

    var padding by remember {
        mutableStateOf(0.dp)
    }

    padding = if (switchClicked) buttonWidth - switchSize - switchPadding * 2 else 0.dp

    val animateSize by animateDpAsState(
        targetValue = if (switchClicked) padding else 0.dp,
        tween(
            durationMillis = 150,
            delayMillis = 0,
            easing = LinearOutSlowInEasing,
        ), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .wrapContentWidth(align = Alignment.End)
            .wrapContentHeight(align = Alignment.CenterVertically)
            .padding(end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(buttonWidth)
                .height(buttonHeight)
                .clip(CircleShape)
                .background(if (switchClicked) primary else outline)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    switchClicked = !switchClicked
                    state(switchClicked)
                }
        ) {
            Row(
                modifier = Modifier
                    .width(buttonWidth)
                    .height(buttonHeight)
                    .padding(switchPadding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(animateSize)
                        .background(Color.Transparent)
                )
                Box(
                    modifier = Modifier
                        .size(switchSize)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
        }
    }
}