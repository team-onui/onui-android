package app.junsu.onui_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.junsu.onui_android.login.LoginScreen
import app.junsu.onui_android.ui.theme.OnuiandroidTheme
import app.junsu.onui_android.navigation.AppNavigationItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnuiandroidTheme {
                BaseApp()
            }
        }
    }
}

@Composable
fun BaseApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppNavigationItem.Login.route) {
        composable(AppNavigationItem.Login.route) {
            LoginScreen(navController = navController)
        }
    }
}