package app.junsu.onui_android.presentation.feature.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.data.request.LoginRequest

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    var token: String = ""
    suspend fun login(sub: String, name: String) {
        kotlin.runCatching {
            ApiProvider.loginApi().login(LoginRequest(sub = sub, name = name))
        }.onSuccess {
            Log.d("token",it.accessToken)
            token = it.accessToken
        }.onFailure {
            Log.d("fail",it.toString())
        }
    }
}
