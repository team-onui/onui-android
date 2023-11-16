package app.junsu.onui_android.data.response.diary

import app.junsu.onui_android.Mood
import com.google.gson.annotations.SerializedName

data class WeekResponse(
    @SerializedName("diaries") val diaries: List<WeekDatas>
) {
    data class WeekDatas(
        @SerializedName("id") val id: String,
        @SerializedName("mood") val mood: Mood,
        @SerializedName("created_at") val created_at: String,
    )
}
