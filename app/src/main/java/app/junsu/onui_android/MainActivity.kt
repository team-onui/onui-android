package app.junsu.onui_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.junsu.onui_android.feature.main.MainScreen
import app.junsu.onui_android.feature.login.LoginScreen
import app.junsu.onui_android.navigation.AppNavigationItem
import app.junsu.onui_android.ui.theme.OnuiandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnuiandroidTheme {
                OnuiApp()
            }
        }
    }
}

@Composable
fun OnuiApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigationItem.Login.route) {
        composable(AppNavigationItem.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(AppNavigationItem.Main.route) {
            MainScreen()
        }
    }
}