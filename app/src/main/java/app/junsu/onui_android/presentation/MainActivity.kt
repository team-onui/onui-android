package app.junsu.onui_android.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.presentation.feature.calendar.CalendarScreen
import app.junsu.onui_android.presentation.feature.chat.RemindScreen
import app.junsu.onui_android.presentation.feature.graph.GraphScreen
import app.junsu.onui_android.presentation.feature.login.LoginScreen
import app.junsu.onui_android.presentation.feature.main.MainScreen
import app.junsu.onui_android.presentation.feature.main.TaskScreen
import app.junsu.onui_android.presentation.feature.main.TaskViewModel
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
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
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
    val timelineViewModel: TimelineViewModel = viewModel()
    val taskViewModel: TaskViewModel = viewModel()
    NavHost(navController = navController, startDestination = AppNavigationItem.Login.route) {
        composable(AppNavigationItem.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(AppNavigationItem.Main.route) {
            MainScreen(
                navController = navController,
                taskViewModel = taskViewModel,
            )
        }
        composable(AppNavigationItem.Remind.route) {
            RemindScreen(navController = navController)
        }
        composable(AppNavigationItem.Calendar.route) {
            CalendarScreen(navController = navController)
        }
        composable(AppNavigationItem.Timeline.route) {
            TimelineScreen(navController = navController, timelineViewModel)
        }
        composable(AppNavigationItem.TimelineDetail.route) {
            TimelineDetail(navController = navController, timelineViewModel)
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
        composable(AppNavigationItem.Task.route) {
            TaskScreen(
                navController = navController,
                taskViewModel = taskViewModel,
            )
        }
    }
}

fun saveDeviceToken(context: Context) {
    try {
        FirebaseApp.initializeApp(context)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            val token = task.result
            val pref = context.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("device_token", token).apply()
            editor.commit()
            CoroutineScope(Dispatchers.IO).launch {
                kotlin.runCatching {
                    ApiProvider.userApi().fetchDeviceToken(token)
                }.onSuccess {
                    Log.d("성공","success")
                }.onFailure {
                    Log.d("fail",it.toString())
                }
            }
        }
    } catch (e: IllegalStateException) {
        Log.d("TAG", "saveDeviceToken: $e")
    }
}