package com.example.holidays.ui.viewmodel

import android.graphics.Path.Op
import android.graphics.Region
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holidays.domain.model.PublicHoliday
import com.example.holidays.domain.usecase.FetchPublicHolidaysUseCase
import com.example.holidays.ui.behavior.FilterBehavior
import com.example.holidays.ui.behavior.FilterBehaviorImpl
import com.example.holidays.util.Operations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OperationsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val holidaysUseCase: FetchPublicHolidaysUseCase
) : ViewModel() {

    val holidays = savedStateHandle.getStateFlow(KEY_FILTERED_HOLIDAYS, listOf<PublicHoliday>())

    private var holidaysPair: Pair<Set<PublicHoliday>, Set<PublicHoliday>>? = null
    private var filterBehavior: FilterBehavior? = null

    fun fetchHolidays(firstCountryCode: String, secondCountryCode: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val firstCountryHolidays =
                    async { holidaysUseCase.execute(null, firstCountryCode) }.await().second
                val secondCountryHolidays =
                    async { holidaysUseCase.execute(null, secondCountryCode) }.await().second

                holidaysPair = Pair(firstCountryHolidays, secondCountryHolidays)

                filter(Operations.Common)
            }
        }
    }

    fun filter(op: Operations) {
        setFilterBehavior(op)

        holidaysPair?.let {
            val filteredList = filterBehavior?.filter(it.first, it.second)
            savedStateHandle[KEY_FILTERED_HOLIDAYS] = filteredList
        }
    }

    fun resetHolidays() {
        savedStateHandle[KEY_FILTERED_HOLIDAYS] = listOf<PublicHoliday>()
    }

    private fun setFilterBehavior(op: Operations) {
        filterBehavior = when (op) {
            is Operations.Common -> FilterBehaviorImpl.Common
            is Operations.ExclusiveA -> FilterBehaviorImpl.ExclusiveA
            is Operations.ExclusiveB -> FilterBehaviorImpl.ExclusiveB
            is Operations.None -> null
        }
    }

    companion object {
        private const val KEY_FILTERED_HOLIDAYS = "ssh_holidays"
    }
}