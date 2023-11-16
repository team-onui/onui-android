package app.junsu.onui_android.presentation.feature.calendar

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.response.diary.DayDiaryResponse
import app.junsu.onui_android.data.response.diary.MonthResponse
import java.time.LocalDate

class CalendarViewModel : ViewModel() {
    private val _currentYear = mutableIntStateOf(LocalDate.now().year)
    private val currentYear: State<Int> = _currentYear

    private val _currentMonth = mutableIntStateOf(LocalDate.now().monthValue)
    private val currentMonth: State<Int> = _currentMonth

    var mood: MonthResponse = MonthResponse(listOf())

    var fetchDiaryState = false

    //lateinit var dayDiary: RemindResponse
    //= RemindResponse(null,"",Mood.BAD, listOf(),"","")
    suspend fun fetchMonthDiary(sharedPreferences: SharedPreferences) {
        kotlin.runCatching {
            ApiProvider.diaryApi().fetchMonthDiary(currentYear.value, currentMonth.value)
        }.onSuccess {
            mood = it
            Log.d("it", it.toString())
            if (it.diaries[0].id.toString() != "") {
                sharedPreferences.edit().putString("id", it.diaries[0].id.toString()).apply()
            }
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun fetchDiary(
        date: String,
        response: (DayDiaryResponse) -> Unit
    ) {
        kotlin.runCatching {
            ApiProvider.diaryApi().fetchDayDiary(date = "${date.substring(0, 4)}-${date.substring(4, 6)}-${date.substring(6, 8)}")
        }.onSuccess {
            Log.d("성공", it.toString())
            fetchDiaryState = true
            response(it)
        }.onFailure {
            Log.d("실패", it.toString())
        }
    }
}