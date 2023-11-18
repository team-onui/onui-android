package app.junsu.onui_android.data.response.diary

import app.junsu.onui_android.Mood
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class DayDiaryResponse(
    @SerializedName("id") val id: UUID,
    @SerializedName("content") val content: String?,
    @SerializedName("mood") val mood: Mood,
    @SerializedName("tag_list") val tagList: List<String>,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("image") val image: String?,
)
