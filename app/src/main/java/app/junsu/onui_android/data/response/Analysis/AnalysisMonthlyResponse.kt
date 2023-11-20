package app.junsu.onui_android.data.response.Analysis

import app.junsu.onui_android.Mood
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class AnalysisMonthlyResponse(
    @SerializedName("list") val list: List<Analysis>?,
    @SerializedName("message") val message: String,
) {
    data class Analysis(
        @SerializedName("id") val id: UUID,
        @SerializedName("mood") val mood: Mood,
        @SerializedName("created_at") val createdAt: String,
    )
}
