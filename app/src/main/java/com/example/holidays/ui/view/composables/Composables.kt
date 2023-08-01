package com.example.holidays.ui.view.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.holidays.ui.view.theme.HolidaysTheme
import com.example.holidays.util.Screen

@OptIn(ExperimentalUnitApi::class)
object Composables {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(
        navController: NavController,
        data: List<String>
    ) {
        Scaffold(
            content = { Recycler(data) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.OpsScreen.route) },
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
            },
            floatingActionButtonPosition = FabPosition.End
        )
    }

    @Composable
    fun OperationsScreen() {
        Column {
            Row {
                RadioButton(selected = false, onClick = { /*TODO*/ })
                RadioButton(selected = false, onClick = { /*TODO*/ })
                RadioButton(selected = false, onClick = { /*TODO*/ })
            }
            Text(text = "Filler text")
        }
    }

    @Composable
    fun Recycler(data: List<String>) {
        val selectedCountries = remember { mutableStateListOf<String>() }

        Column {
            Text(
                text = "Please select a pair of countries",
                fontSize = TextUnit(20f, TextUnitType.Sp),
                color = Color.Black,
                modifier = Modifier.padding(start = 12.dp, top = 16.dp, bottom = 25.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
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