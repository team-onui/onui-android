package app.junsu.onui_android.data.response.timeline

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class CommentResponse(
    @SerializedName("id") val id: UUID,
    @SerializedName("timeline") val timeline: UUID,
    @SerializedName("user_theme") val userTheme: String,
    @SerializedName("content") val content: String,
)
