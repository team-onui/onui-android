package app.junsu.onui_android.presentation.feature

import android.graphics.ColorSpace.Model
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.component.ToggleButton
import app.junsu.onui_android.presentation.ui.theme.gray3
import app.junsu.onui_android.presentation.ui.theme.primary
import app.junsu.onui_android.presentation.ui.theme.surface
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent

@Composable
fun GraphScreen(navController: NavController) {
    var pageNum by rememberSaveable { mutableIntStateOf(0) }
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
            if (pageNum == 0) MaxMood() else MonthMood()
        }
    }
}

@Composable
fun MaxMood() {
    val chart = columnChart()
    //Chart(chart = chart, model = )
}

@Composable
fun MonthMood() {

}