package com.example.holidays.util.enums

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

sealed class FontSizes(val sp: TextUnit) {
    object Header: FontSizes(20.sp)
    object Medium: FontSizes(16.sp)
    object Regular: FontSizes(14.sp)
    object Small: FontSizes(12.sp)
}
