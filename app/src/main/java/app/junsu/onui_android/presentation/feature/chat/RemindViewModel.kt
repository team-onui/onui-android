package app.junsu.onui_android.presentation.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.Mood
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.RemindRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RemindViewModel: ViewModel() {

    fun postMood(
        selectText: String,
        selectMood: Mood,
        selectMoods: List<String>,
        selectImage: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                ApiProvider.diaryApi().postDiary(
                    RemindRequest(
                        content = selectText,
                        mood = selectMood,
                        tag_list = selectMoods,
                        image = selectImage,
                    )
                )
            }.onSuccess {
                Log.d("성공", "tjd")
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }
}