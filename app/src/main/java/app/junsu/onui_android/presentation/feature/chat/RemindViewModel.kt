package app.junsu.onui_android.presentation.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.Mood
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.RemindRequest
import app.junsu.onui_android.data.response.user.ProfileResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RemindViewModel : ViewModel() {

    var profile = ProfileResponse("", "", "", "", false)

    suspend fun fetchProfile() {
        kotlin.runCatching {
            ApiProvider.userApi().fetchProfile()
        }.onSuccess {
            profile = it
            Log.d("profile", profile.toString())
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

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