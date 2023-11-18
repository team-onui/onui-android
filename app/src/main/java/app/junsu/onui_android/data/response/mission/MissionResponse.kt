package app.junsu.onui_android.data.response.mission

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class MissionResponse(
    @SerializedName("missions") val missions: List<Missions>,
) {
    data class Missions(
        @SerializedName("id") val id: UUID,
        @SerializedName("name") val name: String,
        @SerializedName("goal") val goal: String,
        @SerializedName("message") val message: String,
        @SerializedName("mission_type") val missionType: String,
        @SerializedName("coast") val coast: Int?,
        @SerializedName("is_finished") val isFinished: Boolean
    )
}