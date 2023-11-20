package app.junsu.onui_android.presentation.navigation

sealed class AppNavigationItem(val route: String) {

    object Login: AppNavigationItem("login")

    object Main: AppNavigationItem("main")

    object Remind: AppNavigationItem("remind")

    object Calendar: AppNavigationItem("calendar")

    object Timeline: AppNavigationItem("timeline")

    object TimelineDetail: AppNavigationItem("timelineDetail")

    object SunStore: AppNavigationItem("sunStore")

    object MoonStore: AppNavigationItem("moonStore")

    object Settings: AppNavigationItem("settings")

    object Palette: AppNavigationItem("palette")

    object Task: AppNavigationItem("task")

    object Graph: AppNavigationItem("graph")

    object Theme: AppNavigationItem("theme")
}