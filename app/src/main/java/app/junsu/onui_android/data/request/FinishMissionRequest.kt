package app.junsu.onui_android.data.request

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FinishMissionRequest(
    @SerializedName("mission_id") val missionId: UUID
)
