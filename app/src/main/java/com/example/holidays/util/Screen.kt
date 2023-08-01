package com.example.holidays.util

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object OpsScreen : Screen("ops_screen")
}
