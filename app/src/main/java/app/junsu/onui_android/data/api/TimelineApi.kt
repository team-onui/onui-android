package app.junsu.onui_android.data.api

import app.junsu.onui_android.data.request.CommentRequest
import app.junsu.onui_android.data.response.timeline.CommentResponse
import app.junsu.onui_android.data.response.timeline.TimeLineResponse
import app.junsu.onui_android.data.response.timeline.TimelineCommentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID

interface TimelineApi {
    @GET("tl")
    suspend fun fetchTimeLine(
        @Query("idx") idx: Int,
        @Query("size") size: Int,
        @Query("date") date: String,
    ): TimeLineResponse

    @GET("comment")
    suspend fun fetchComment(
        @Query("timeline_id") timeline_id: UUID,
    ): TimelineCommentResponse

    @POST("comment")
    suspend fun comment(
        @Query("timeline_id") timeline_id: String,
        @Body commentRequest: CommentRequest,
    ): TimelineCommentResponse.Comment
}