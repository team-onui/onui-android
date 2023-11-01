package app.junsu.onui_android.feature.calendar

import android.widget.CalendarView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Calendar() {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    val date = Date()

    var yearState by remember { mutableStateOf(formatter.format(date).split("-").first()) }
    var monthState by remember { mutableStateOf(formatter.format(date).split("-")[1]) }
    var dayState by remember { mutableStateOf(formatter.format(date).split("-").last()) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { CalendarView(it) }
            ) { calendarView ->
                val selectedDate = "${yearState}-${monthState}-${dayState}"
                calendarView.date = formatter.parse(selectedDate)!!.time

                calendarView.setOnDateChangeListener { _, year, month, day ->

                    yearState = year.toString()
                    monthState = (month + 1).toString()
                    dayState = day.toString()
                }
            }
        }
    }
}