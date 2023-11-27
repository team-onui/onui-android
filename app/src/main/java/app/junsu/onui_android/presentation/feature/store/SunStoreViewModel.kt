package app.junsu.onui_android.presentation.feature.store

import android.util.Log
import androidx.lifecycle.ViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.ThemeRequest
import app.junsu.onui_android.data.response.user.AllThemeResponse
import app.junsu.onui_android.data.response.user.BuyThemeResponse
import app.junsu.onui_android.data.response.user.ProfileResponse
import app.junsu.onui_android.data.response.user.ThemeResponse

class SunStoreViewModel : ViewModel() {

    var theme: BuyThemeResponse = BuyThemeResponse(listOf())

    var themeList = AllThemeResponse(listOf())

    var rice = ""

    var profile = ProfileResponse("", "", "", "", false)

    suspend fun buyTheme(id: String) {
        kotlin.runCatching {
            ApiProvider.userApi().buyTheme(id)
        }.onSuccess {
            theme = it
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun fetchRice() {
        kotlin.runCatching {
            ApiProvider.userApi().fetchRice()
        }.onSuccess {
            rice = it.rice
        }.onFailure {
            Log.d("fail", it.toString())
        }
    }

    suspend fun fetchAllTheme() {
        kotlin.runCatching {
            ApiProvider.userApi().fetchAllTheme()
        }.onSuccess {
            themeList = it
            Log.d("it",it.toString())
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

}