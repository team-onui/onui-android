package app.junsu.onui_android.data.response.diary

import app.junsu.onui_android.Mood
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class MonthResponse(
    val diaries: List<DayMood>
) {
    data class DayMood(
        @SerializedName("id") val id: UUID,
        @SerializedName("mood") val mood: Mood,
        @SerializedName("created_at") val createdAt: String,
    )
}