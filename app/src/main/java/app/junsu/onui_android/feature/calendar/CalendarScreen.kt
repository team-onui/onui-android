package app.junsu.onui_android.feature.calendar

import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    //Calendar()
    val pickerState = rememberDatePickerState()
    DatePicker(
        state = pickerState,
        headline = {  },
        title = {  }
    )
}