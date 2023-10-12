package app.junsu.onui_android.navigation

sealed class AppNavigationItem(val route: String) {

    object Login: AppNavigationItem("login")
}