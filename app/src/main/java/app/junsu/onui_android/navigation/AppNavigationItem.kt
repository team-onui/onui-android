package com.example.yuseongallowancepaymentsandroid.navigation

sealed class AppNavigationItem(val route: String) {

    object Login: AppNavigationItem("login")
}