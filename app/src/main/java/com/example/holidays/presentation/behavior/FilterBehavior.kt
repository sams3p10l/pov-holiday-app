package com.example.holidays.presentation.behavior

import com.example.holidays.domain.model.PublicHoliday

fun interface FilterBehavior {
    fun filter(listA: Set<PublicHoliday>, listB: Set<PublicHoliday>): List<PublicHoliday>
}