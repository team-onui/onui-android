package app.junsu.onui_android.presentation.feature.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.presentation.component.Header
import app.junsu.onui_android.presentation.ui.theme.gray3
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PaletteScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: SettingViewModel = viewModel()
    val colorList = listOf(
        R.color.color_0,
        R.color.color_1,
        R.color.color_2,
        R.color.color_3,
        R.color.color_4,
        R.color.color_5,
        R.color.color_6,
        R.color.color_7,
        R.color.color_8,
        R.color.color_9,
        R.color.color_10,
        R.color.color_11,
        R.color.color_12,
        R.color.color_13,
        R.color.color_14,
        R.color.color_15,
        R.color.color_16,
        R.color.color_17,
        R.color.color_18,
        R.color.color_19,
    )
    val hexColorList = arrayListOf<String>()

    for (colorId in colorList) {
        val colorInt = ContextCompat.getColor(context, colorId)
        val hexColor = String.format("%06X", 0xFFFFFF and colorInt)
        hexColorList.add(hexColor)
    }
    Log.d("color", hexColorList.toString())
    Column(modifier = Modifier.fillMaxSize()) {
        Header(
            title = "색상 선택",
            modifier = Modifier.clickable { navController.popBackStack() }
        )
        FlowRow(
            maxItemsInEachRow = 4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(colorList.size) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .fillMaxWidth(0.2f)
                        .height(120.dp)
                        .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = gray3)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = colorResource(id = colorList[it]))
                        .clickable {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.fetchProfileTheme(hexColorList[it])
                            }
                        }
                )
            }
        }
    }
}