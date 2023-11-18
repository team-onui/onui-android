package app.junsu.onui_android.presentation.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider

class TaskViewModel: ViewModel() {

    suspend fun fetchTask() {
        kotlin.runCatching {
            ApiProvider.missionApi().fetchMission()
        }.onSuccess {
            Log.d("mission",it.toString())
        }.onFailure {
            Log.d("fail",it.toString())
        }
    }
}