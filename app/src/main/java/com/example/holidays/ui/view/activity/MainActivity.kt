package com.example.holidays.ui.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.holidays.ui.view.composables.Composables
import com.example.holidays.ui.viewmodel.MainScreenViewModel
import com.example.holidays.ui.viewmodel.OperationsScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainScreenViewModel>()
    private val operationsViewModel by viewModels<OperationsScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Composables.NavigationHost(mainViewModel, operationsViewModel)
        }
    }

}