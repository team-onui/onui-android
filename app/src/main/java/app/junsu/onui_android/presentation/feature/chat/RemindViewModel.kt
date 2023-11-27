package app.junsu.onui_android.presentation.feature.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.Mood
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.ChatRequest
import app.junsu.onui_android.data.request.RemindRequest
import app.junsu.onui_android.data.response.diary.ChatResponse
import app.junsu.onui_android.data.response.user.ProfileResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class RemindViewModel : ViewModel() {

    var profile = ProfileResponse("", "", "", "", false)
    var chatResponse = ChatResponse("")
    var id = ""

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
                id = it.id.toString()
                Log.d("성공", "tjd")
            }.onFailure {
                Log.d("TEST", it.toString())
            }
        }
    }

    suspend fun fetchChat(tagList: List<String>) {
        kotlin.runCatching {
            ApiProvider.diaryApi().fetchChat(ChatRequest(tagList))
        }.onSuccess {
            chatResponse = it
            Log.d("it",it.message)
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun postTimeLine(id: String) {
        kotlin.runCatching {
            ApiProvider.timelineApi().postTimeline(id)
        }.onSuccess {
            Log.d("success", "success")
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }
}