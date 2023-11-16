package app.junsu.onui_android.presentation.feature.login

import android.app.Application
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.AndroidViewModel
import app.junsu.onui.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.api_key))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(application, gso)
    }

    fun performGoogleSignIn(activityLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        activityLauncher.launch(signInIntent)
    }

    fun handleGoogleSignInResult(result: ActivityResult) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.getResult(ApiException::class.java)

            val userName = account.givenName
            val serverAuth = account.serverAuthCode
            Log.d("user", userName.toString())
            Log.d("server", serverAuth.toString())

            // 로그인 성공 후 추가적인 작업을 수행할 수 있습니다.

        } catch (e: ApiException) {
            Log.e("fail", e.stackTraceToString())
            // 로그인 실패 시 처리할 로직을 추가할 수 있습니다.
        }
    }
}
