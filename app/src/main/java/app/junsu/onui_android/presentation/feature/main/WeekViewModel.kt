package app.junsu.onui_android.presentation.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.response.diary.WeekResponse
import app.junsu.onui_android.imageState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeekViewModel : ViewModel() {

    var weekData: WeekResponse? = WeekResponse(listOf())

    suspend fun fetchWeekData() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                ApiProvider.diaryApi().fetchWeekDiary()
            }.onSuccess {
                weekData = it
                Log.d("TEST", it.toString())
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }

    suspend fun fetchTheme() {
        kotlin.runCatching {
            ApiProvider.userApi().fetchProfile()
        }.onSuccess {
            imageState = when (it.theme) {
                "애옹쓰" -> 1
                "홍조쓰" -> 2
                "새싹쓰" -> 3
                else -> 0
            }
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }
}