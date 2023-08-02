package com.example.holidays.ui.behavior

import com.example.holidays.domain.model.PublicHoliday

fun interface FilterBehavior {
    fun filter(listA: Set<PublicHoliday>, listB: Set<PublicHoliday>): List<PublicHoliday>
}