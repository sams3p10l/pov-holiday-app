package com.example.holidays.ui.view.composables

import android.annotation.SuppressLint
import android.widget.RadioGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.holidays.ui.view.theme.PurpleGrey40
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.holidays.ui.view.theme.HolidaysTheme
import com.example.holidays.ui.viewmodel.MainScreenViewModel
import com.example.holidays.util.Operations
import com.example.holidays.util.Screen.MainScreen
import com.example.holidays.util.Screen.OpsScreen
import com.example.holidays.util.Screen.OpsScreen.ARG_COUNTRY1
import com.example.holidays.util.Screen.OpsScreen.ARG_COUNTRY1_CODE
import com.example.holidays.util.Screen.OpsScreen.ARG_COUNTRY2
import com.example.holidays.util.Screen.OpsScreen.ARG_COUNTRY2_CODE

@OptIn(ExperimentalUnitApi::class)
object Composables {

    @Composable
    fun NavigationHost(mainViewModel: MainScreenViewModel) {
        HolidaysTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = MainScreen.route
                ) {
                    composable(route = MainScreen.route) {
                        MainScreen(navController, mainViewModel)
                    }
                    composable(
                        route = OpsScreen.route + "/{$ARG_COUNTRY1}/{$ARG_COUNTRY2}/{$ARG_COUNTRY1_CODE}/{$ARG_COUNTRY2_CODE}",
                        arguments = listOf(
                            navArgument(ARG_COUNTRY1) {
                                type = NavType.StringType
                            },
                            navArgument(ARG_COUNTRY2) {
                                type = NavType.StringType
                            },
                            navArgument(ARG_COUNTRY1_CODE) {
                                type = NavType.StringType
                            },
                            navArgument(ARG_COUNTRY2_CODE) {
                                type = NavType.StringType
                            }
                        )
                    ) { entry ->
                        OperationsScreen(
                            country1 = entry.arguments?.getString(ARG_COUNTRY1),
                            country2 = entry.arguments?.getString(ARG_COUNTRY2),
                            country1Code = entry.arguments?.getString(ARG_COUNTRY1_CODE),
                            country2Code = entry.arguments?.getString(ARG_COUNTRY2_CODE)
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        navController: NavController,
        viewModel: MainScreenViewModel
    ) {
        val selectedCountries = remember { mutableStateListOf<String>() }
        var isFabEnabled by remember { mutableStateOf(false) }

        isFabEnabled = selectedCountries.size == 2

        Scaffold(
            content = { Recycler(viewModel, selectedCountries) },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = isFabEnabled,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth }
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth }
                    )
                ) {
                    FloatingActionButton(
                        onClick = {
                            val firstCountryCode = viewModel.getCountryCode(selectedCountries[0])
                            val secondCountryCode = viewModel.getCountryCode(selectedCountries[1])

                            if (firstCountryCode != null && secondCountryCode != null) {
                                navController.navigate(
                                    OpsScreen.withArgs(
                                        selectedCountries[0],
                                        selectedCountries[1],
                                        firstCountryCode,
                                        secondCountryCode
                                    )
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(end = 8.dp, bottom = 16.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Next >",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }

    @Composable
    fun OperationsScreen(
        country1: String?,
        country2: String?,
        country1Code: String?,
        country2Code: String?
    ) {
        Column {
            Header(text = "$country1 & $country2's holidays")
            OpsRadioGroup(country1, country2)
            Spacer(Modifier.height(20.dp))
            Text(text = country1!!)
            Text(text = country2!!)
            Text(text = country1Code!!)
            Text(text = country2Code!!)
        }
    }

    @Composable
    fun OpsRadioGroup(
        country1: String?,
        country2: String?
    ) {
        var selectedButton by remember { mutableStateOf<Operations>(Operations.None) }

        Column {
            RadioButtonOption(
                text = Operations.Common.text,
                selected = selectedButton == Operations.Common
            ) {
                selectedButton = Operations.Common
            }
            RadioButtonOption(
                text = Operations.ExclusiveA.text + "$country1",
                selected = selectedButton == Operations.ExclusiveA
            ) {
                selectedButton = Operations.ExclusiveA
            }
            RadioButtonOption(
                text = Operations.ExclusiveB.text + "$country2",
                selected = selectedButton == Operations.ExclusiveB
            ) {
                selectedButton = Operations.ExclusiveB
            }
        }
    }

    @Composable
    fun RadioButtonOption(
        text: String,
        selected: Boolean,
        onClick: () -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onClick,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(text = text)
        }
    }

    @Composable
    fun Recycler(viewModel: MainScreenViewModel, selectedCountries: MutableList<String>) {
        val dataState = viewModel.countriesBindings.collectAsState()
        val data = dataState.value
        val lazyListState = rememberLazyListState()

        Column {
            Header(text = "Please select a pair of countries")
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .simpleVerticalScrollbar(state = lazyListState, 4.dp)
            ) {
                items(data) { listData ->
                    CountryView(listData, selectedCountries)
                }
            }
        }
    }

    @Composable
    fun CountryView(countryName: String, selectedCountries: MutableList<String>) {
        val isSelected = selectedCountries.contains(countryName)

        Text(
            text = countryName,
            textAlign = TextAlign.Center,
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.Magenta else Color.Black,
            modifier = Modifier.clickable {
                if (isSelected) {
                    selectedCountries.remove(countryName)
                } else {
                    if (selectedCountries.size >= 2) {
                        selectedCountries.removeAt(0)
                    }
                    selectedCountries.add(countryName)
                }
            }
        )
    }

    @Composable
    fun Header(text: String) {
        Column {
            Text(
                text = text,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp, top = 16.dp, bottom = 10.dp)
            )
            Divider(thickness = 1.dp)
        }
    }

    /**
     * https://stackoverflow.com/questions/66341823/jetpack-compose-scrollbars; modified
     */
    private fun Modifier.simpleVerticalScrollbar(
        state: LazyListState,
        width: Dp = 8.dp
    ): Modifier = composed {
        val targetAlpha = if (state.isScrollInProgress) 1f else 0f
        val duration = if (state.isScrollInProgress) 150 else 500

        val alpha by animateFloatAsState(
            targetValue = targetAlpha,
            animationSpec = tween(durationMillis = duration)
        )

        drawWithContent {
            drawContent()

            val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
            val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

            // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
            if (needDrawScrollbar && firstVisibleElementIndex != null) {
                val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
                val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
                val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

                drawRect(
                    color = PurpleGrey40,
                    topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                    size = Size(width.toPx(), scrollbarHeight),
                    alpha = alpha
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecyclerPreview() {
    val list = listOf("PERA", "MIKA", "LAZA")
    HolidaysTheme {
        val modifier = Modifier
            .fillMaxSize()

        LazyColumn(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            items(list) { listData ->
                Composables.CountryView(listData, mutableListOf())
            }
        }
    }
}