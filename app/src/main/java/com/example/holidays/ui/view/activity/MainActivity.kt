package com.example.holidays.ui.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.holidays.ui.view.composables.Composables
import com.example.holidays.ui.view.theme.HolidaysTheme
import com.example.holidays.ui.viewmodel.MainScreenViewModel
import com.example.holidays.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HolidaysTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
                        composable(route = Screen.MainScreen.route) {
                            Composables.MainScreen(navController, mainViewModel)
                        }
                        composable(route = Screen.OpsScreen.route) {
                            Composables.OperationsScreen()
                        }
                    }
                }
            }
        }
    }

}