package app.junsu.onui_android.presentation.feature.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.FilterRequest
import app.junsu.onui_android.data.request.ProfileRequest

class SettingViewModel : ViewModel() {

    var profile = ProfileRequest("", "", "", false)
    suspend fun fetchProfile() {
        kotlin.runCatching {
            ApiProvider.userApi().fetchProfile()
        }.onSuccess {
            profile = it
            Log.d("profile", profile.toString())
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun changeFilter(state: Boolean) {
        kotlin.runCatching {
            ApiProvider.userApi().changeFilter(FilterRequest(state))
        }.onSuccess {
            profile = it
            Log.d("profile", profile.toString())
        }.onFailure {
            Log.d("fail", state.toString())
        }
    }

    suspend fun deleteUser(state: (Boolean) -> Unit) {
        kotlin.runCatching {
            ApiProvider.userApi().deleteUser()
        }.onSuccess {
            state(true)
        }.onFailure {
            Log.d("fail",it.toString())
        }
    }
}