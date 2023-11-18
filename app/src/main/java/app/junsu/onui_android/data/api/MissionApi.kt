package app.junsu.onui_android.data.api

import app.junsu.onui_android.data.response.mission.MissionResponse
import retrofit2.http.GET

interface MissionApi {

    @GET("mission")
    suspend fun fetchMission(): MissionResponse
}