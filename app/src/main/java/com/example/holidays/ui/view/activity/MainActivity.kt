package com.example.holidays.ui.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.holidays.domain.repository.HolidaysUseCaseRepo
import com.example.holidays.ui.view.composables.Composables
import com.example.holidays.ui.view.theme.HolidaysTheme
import com.example.holidays.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var holidaysUseCaseRepo: HolidaysUseCaseRepo

    private var countriesData: List<String> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking(context = Dispatchers.IO) {
            try {
                countriesData = holidaysUseCaseRepo.fetchCountries().second.map { it.name }
            } catch (e: HttpException) {
                Log.e("MainActivity", "onCreate: ", e)
            }
        }

        setContent {
            HolidaysTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
                        composable(route = Screen.MainScreen.route) {
                            Composables.MainScreen(navController, countriesData)
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