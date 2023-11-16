package app.junsu.onui_android.presentation.feature.store

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.ProfileRequest
import app.junsu.onui_android.data.request.ThemeRequest

class SunStoreViewModel: ViewModel() {

    var profile = ProfileRequest("","","",false)
    suspend fun changeTheme(text: String) {
        kotlin.runCatching {
            ApiProvider.userApi().changeTheme(ThemeRequest(text))
        }.onSuccess {
            profile = it
            Log.d("profile",profile.toString())
        }.onFailure {
            Log.d("fail",it.toString())
        }
    }
}