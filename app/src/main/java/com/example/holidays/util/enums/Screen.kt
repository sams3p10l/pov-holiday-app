package com.example.holidays.util.enums

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object OpsScreen : Screen("ops_screen") {
        const val ARG_COUNTRY1 = "country1"
        const val ARG_COUNTRY2 = "country2"
        const val ARG_COUNTRY1_CODE = "country1Code"
        const val ARG_COUNTRY2_CODE = "country2Code"
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
