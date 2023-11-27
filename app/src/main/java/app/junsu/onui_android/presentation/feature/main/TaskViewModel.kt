package app.junsu.onui_android.presentation.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.FinishMissionRequest
import app.junsu.onui_android.data.response.mission.MissionResponse
import java.util.UUID

class TaskViewModel : ViewModel() {

    var rice = ""

    var task = MissionResponse(listOf())

    suspend fun fetchRice() {
        kotlin.runCatching {
            ApiProvider.userApi().fetchRice()
        }.onSuccess {
            rice = it.rice
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun fetchTask() {
        kotlin.runCatching {
            ApiProvider.missionApi().fetchMission()
        }.onSuccess {
            Log.d("mission", it.toString())
            task = it
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun finishMission(id: UUID) {
        kotlin.runCatching {
            ApiProvider.missionApi().finishMission(FinishMissionRequest(id))
        }.onSuccess {
            task = it
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }
}