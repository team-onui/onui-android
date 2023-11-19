package app.junsu.onui_android.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.junsu.onui.R
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.presentation.feature.GraphScreen
import app.junsu.onui_android.presentation.feature.calendar.CalendarScreen
import app.junsu.onui_android.presentation.feature.chat.RemindScreen
import app.junsu.onui_android.presentation.feature.login.LoginScreen
import app.junsu.onui_android.presentation.feature.main.MainScreen
import app.junsu.onui_android.presentation.feature.settings.PaletteScreen
import app.junsu.onui_android.presentation.feature.settings.SettingsScreen
import app.junsu.onui_android.presentation.feature.settings.ThemeScreen
import app.junsu.onui_android.presentation.feature.store.MoonStoreScreen
import app.junsu.onui_android.presentation.feature.store.SunStoreScreen
import app.junsu.onui_android.presentation.feature.timeline.TimelineDetail
import app.junsu.onui_android.presentation.feature.timeline.TimelineScreen
import app.junsu.onui_android.presentation.feature.timeline.TimelineViewModel
import app.junsu.onui_android.presentation.navigation.AppNavigationItem
import app.junsu.onui_android.presentation.ui.theme.OnuiandroidTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope

class MainActivity : ComponentActivity() {

    private fun getGoogleClient(context: Context): GoogleSignInClient {
        val googleSignInOption =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
                .requestServerAuthCode(getString(R.string.client_id))
                .requestEmail()
                .build()

        return GoogleSignIn.getClient(context, googleSignInOption)
    }

    fun requestGoogleLogin(context: Context) {
        val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient(context = context) }
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }


    private val googleAuthLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)

                val userName = account.givenName
                val serverAuth = account.serverAuthCode
                Log.d("user", userName.toString())
                Log.d("server", serverAuth.toString())


            } catch (e: ApiException) {
                Log.e("fail", e.stackTraceToString())
            }
        }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnuiandroidTheme {
                OnuiApp()
            }
        }
        ApiProvider.initialize(applicationContext)
    }
}

@Composable
fun OnuiApp() {
    val navController = rememberNavController()
    val viewModel: TimelineViewModel = viewModel()
    NavHost(navController = navController, startDestination = AppNavigationItem.Graph.route) {
        composable(AppNavigationItem.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(AppNavigationItem.Main.route) {
            MainScreen(navController = navController)
        }
        composable(AppNavigationItem.Remind.route) {
            RemindScreen(navController = navController)
        }
        composable(AppNavigationItem.Calendar.route) {
            CalendarScreen(navController = navController)
        }
        composable(AppNavigationItem.Timeline.route) {
            TimelineScreen(navController = navController, viewModel)
        }
        composable(AppNavigationItem.TimelineDetail.route) {
            TimelineDetail(navController = navController, viewModel)
        }
        composable(AppNavigationItem.SunStore.route) {
            SunStoreScreen(navController = navController)
        }
        composable(AppNavigationItem.MoonStore.route) {
            MoonStoreScreen(navController = navController)
        }
        composable(AppNavigationItem.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(AppNavigationItem.Palette.route) {
            PaletteScreen(navController = navController)
        }
        composable(AppNavigationItem.Graph.route) {
            GraphScreen(navController = navController)
        }
        composable(AppNavigationItem.Theme.route) {
            ThemeScreen(navController = navController)
        }
    }
}