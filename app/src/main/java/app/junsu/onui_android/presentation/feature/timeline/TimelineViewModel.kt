package app.junsu.onui_android.presentation.feature.timeline

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.CommentRequest
import app.junsu.onui_android.data.response.timeline.TimeLineResponse
import app.junsu.onui_android.data.response.timeline.TimelineCommentResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class TimelineViewModel : ViewModel() {

    var timeline = TimeLineResponse(listOf(), true)
    var timelineComment = TimelineCommentResponse(listOf()).commentList
    var fetchDate = LocalDate.now()
    var index = 0

    suspend fun fetchTimeLine(
        idx: Int,
        size: Int,
        date: String,
        response: (TimeLineResponse) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                ApiProvider.timelineApi().fetchTimeLine(
                    idx = idx, size = size, date = date,
                )
            }.onSuccess {
                timeline = it
                response(it)
                Log.d("it", it.toString())
            }.onFailure {
                Log.d("fail", it.toString())
            }
        }
    }

    suspend fun fetchTimelineComment() {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                ApiProvider.timelineApi().fetchComment(timelineId = timeline.content!![index].id)
            }.onSuccess {
                timelineComment = it.commentList
                Log.d("success", it.toString())
            }.onFailure {
                Log.d("fail", it.toString())
            }
        }
    }

    suspend fun postComment(text: String) {
        kotlin.runCatching {
            Log.d("sdf", timeline.content!![0].id.toString())
            ApiProvider.timelineApi().comment(
                timelineId = timeline.content!![0].id.toString(),
                commentRequest = CommentRequest(text)
            )
        }.onSuccess {
            Log.d("성공", "성공")
            timelineComment = timelineComment?.plus(it)
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }
}