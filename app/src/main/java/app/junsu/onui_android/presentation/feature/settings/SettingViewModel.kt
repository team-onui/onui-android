package app.junsu.onui_android.presentation.feature.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.FilterRequest
import app.junsu.onui_android.data.request.NameRequest
import app.junsu.onui_android.data.request.ProfileThemeRequest
import app.junsu.onui_android.data.request.ThemeRequest
import app.junsu.onui_android.data.response.user.ProfileResponse
import app.junsu.onui_android.data.response.user.ThemeResponse

class SettingViewModel : ViewModel() {

    var profile = ProfileResponse("", "", "", "", false)
    var profileTheme = ProfileResponse("", "", "", "", false)
    var userName = ""
    var themeList = ThemeResponse(listOf())
    suspend fun getTheme() {
        kotlin.runCatching {
            ApiProvider.userApi().getTheme()
        }.onSuccess {
            Log.d("it",it.toString())
            themeList = it
        }.onFailure {
            Log.d("fail",it.toString())
        }
    }

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
            state(true)
            Log.d("fail", it.toString())
        }
    }

    suspend fun fetchProfileTheme(code: String) {
        kotlin.runCatching {
            ApiProvider.userApi().patchProfileTheme(ProfileThemeRequest(code))
        }.onSuccess {
            profileTheme = it
            Log.d("profile", it.theme)
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun patchName(name: String) {
        kotlin.runCatching {
            ApiProvider.userApi().patchName(NameRequest(name))
        }.onSuccess {
            userName = it.name
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }
}