package app.junsu.onui_android.presentation.feature.graph

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui_android.data.response.Analysis.AnalysisMonthlyResponse
import app.junsu.onui_android.data.response.Analysis.AnalysisMoodResponse
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.component.ToggleButton
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.outline
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.surface
import kotlin.math.roundToInt

@Composable
fun GraphScreen(navController: NavController) {
    var pageNum by rememberSaveable { mutableIntStateOf(0) }
    val viewModel: GraphViewModel = viewModel()
    LaunchedEffect(Unit) {
        viewModel.fetchAnalysis()
        viewModel.fetchAnalysisMonthly()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gray3)
    ) {
        Header(title = "분석 그래프", modifier = Modifier.clickable { navController.popBackStack() })
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(surface)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            ToggleButton(
                items = arrayOf("가장 많은 감정", "한 달 감정 변화"),
                position = pageNum
            ) {
                pageNum = it
            }
            if (pageNum == 0) MaxMood(viewModel.analysisMoodResponse) else MonthMood(viewModel.analysisMonthlyResponse)
        }
    }
}

@Composable
fun MaxMood(analysisMoodResponse: AnalysisMoodResponse) {
    val data = listOf(
        analysisMoodResponse.good.toFloat(),
        analysisMoodResponse.fine.toFloat(),
        analysisMoodResponse.notBad.toFloat(),
        analysisMoodResponse.bad.toFloat(),
        analysisMoodResponse.worst.toFloat(),
    )
    val labels = listOf("매우 좋음", "좋음", "보통", "나쁨", "매우 나쁨")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawChart(data, labels, size)
        }
    }
}

@Composable
fun MonthMood(analysisMonthlyResponse: AnalysisMonthlyResponse) {
    val list = listOf(10f, 30f, 3f, 1f)
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        val zipList: List<Pair<Float, Float>> = list.zipWithNext()
        val max = list.max()
        val min = list.min()

        val lineColor =
            if (list.last() > list.first()) primary else outline

        for (pair in zipList) {

            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
            val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                onDraw = {
                    val fromPoint =
                        Offset(x = 0f, y = size.height.times(1 - fromValuePercentage))
                    val toPoint =
                        Offset(x = size.width, y = size.height.times(1 - toValuePercentage))

                    drawLine(
                        color = lineColor,
                        start = fromPoint,
                        end = toPoint,
                        strokeWidth = 3f
                    )
                })
        }
    }
}

fun DrawScope.drawChart(data: List<Float>, labels: List<String>, size: Size) {
    val padding = 16f
    val width = size.width - padding * 2
    val height = size.height - padding * 2
    val maxData = data.maxOrNull() ?: 0f

    val totalBars = data.size
    val totalGapWidth = (totalBars - 1) * 8.dp.toPx()
    val barWidth = (width - totalGapWidth) / totalBars
    val colorList = listOf(
        Color(0xFFFA8BAC),
        Color(0xFFFFCF71),
        Color(0xFFF4F1D7),
        Color(0xFF4F9568),
        Color(0xFF6B7773),
    )

    drawRoundRect(
        color = Color(0xFFD9D9D9),
        size = Size(width, height),
        cornerRadius = CornerRadius(100f, 100f),
        topLeft = Offset(padding, padding)
    )

    data.forEachIndexed { index, value ->
        val barHeight = (value / maxData) * height
        val xPosition = padding + index * (barWidth + 8.dp.toPx())
        drawRoundRect(
            color = colorList[index],
            size = Size(barWidth, barHeight),
            cornerRadius = CornerRadius(30f, 30f),
            topLeft = Offset(xPosition, padding + height - barHeight)
        )

        drawIntoCanvas {
            val text = labels.getOrElse(index) { "" }
            val paint = Paint().apply { textSize = 34f }
            val labelX = xPosition + barWidth / 2 - padding * 2
            val labelY = padding + height + 34f
            it.nativeCanvas.drawText(text, labelX, labelY, paint)
        }
    }

    val yLabels = (0..5).map { (maxData * it / 5).roundToInt().toString() }
    yLabels.forEachIndexed { index, label ->
        val xPosition = padding * 2 + (height - index * height / 5)
        drawIntoCanvas {
            val text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(label) }
            }
            it.nativeCanvas.drawText(text.text, padding, xPosition, Paint().apply { textSize = 30f })
        }
    }
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float) =
    (value - min) / (max - min)