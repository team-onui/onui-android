package app.junsu.onui_android.data.api

import app.junsu.onui_android.data.request.FinishMissionRequest
import app.junsu.onui_android.data.response.mission.MissionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MissionApi {

    @GET("mission")
    suspend fun fetchMission(): MissionResponse

    @POST("mission")
    suspend fun finishMission(
        @Body finishMissionRequest: FinishMissionRequest
    ): MissionResponse
}