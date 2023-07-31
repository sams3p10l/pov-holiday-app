package com.example.holidays.ui.view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

object Composables {

    @OptIn(ExperimentalUnitApi::class)
    @Composable
    fun CountryView(countryName: String) {
        Text(
            text = countryName,
            textAlign = TextAlign.Center,
            fontSize = TextUnit(16f, TextUnitType.Sp)
        )
    }
}