package app.junsu.onui_android.data.response.timeline

import app.junsu.onui_android.Mood
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class TimeLineResponse(
    @SerializedName("content") val content: List<TimeLineResponses>?,
    @SerializedName("last") val last: Boolean,
) {
    data class TimeLineResponses(
        @SerializedName("id") val id: UUID,
        @SerializedName("content") val content: String,
        @SerializedName("mood") val mood: Mood,
        @SerializedName("tag_list") val tagList: List<String>,
        @SerializedName("image") val image: String?,
        @SerializedName("writer") val writer: String,
        @SerializedName("comment_count") val commentCount: Int,
    )
}