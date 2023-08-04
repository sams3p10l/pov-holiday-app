package com.example.holidays.presentation.behavior

import com.example.holidays.domain.model.PublicHoliday

sealed class FilterBehaviorImpl: FilterBehavior {
    object Common: FilterBehaviorImpl() {
        override fun filter(
            listA: Set<PublicHoliday>,
            listB: Set<PublicHoliday>
        ): List<PublicHoliday> {
            return listA.intersect(listB).toList()
        }
    }

    object ExclusiveA: FilterBehaviorImpl() {
        override fun filter(
            listA: Set<PublicHoliday>,
            listB: Set<PublicHoliday>
        ): List<PublicHoliday> {
            return listA.subtract(listB).toList()
        }
    }

    object ExclusiveB: FilterBehaviorImpl() {
        override fun filter(
            listA: Set<PublicHoliday>,
            listB: Set<PublicHoliday>
        ): List<PublicHoliday> {
            return listB.subtract(listA).toList()
        }
    }
}
