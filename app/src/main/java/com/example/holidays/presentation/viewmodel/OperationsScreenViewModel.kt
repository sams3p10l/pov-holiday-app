package com.example.holidays.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holidays.domain.model.PublicHoliday
import com.example.holidays.domain.usecase.FetchPublicHolidaysUseCase
import com.example.holidays.presentation.behavior.FilterBehavior
import com.example.holidays.presentation.behavior.FilterBehaviorImpl
import com.example.holidays.util.ContextProvider
import com.example.holidays.util.enums.Operations
import com.example.holidays.util.enums.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class OperationsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val holidaysUseCase: FetchPublicHolidaysUseCase,
    private val contextProvider: ContextProvider
) : ViewModel() {

    val holidays = savedStateHandle.getStateFlow(KEY_FILTERED_HOLIDAYS, listOf<PublicHoliday>())

    private val _errorState: MutableSharedFlow<Int?> = MutableSharedFlow()
    val errorState = _errorState.asSharedFlow()

    @VisibleForTesting
    var holidaysPair: Pair<Set<PublicHoliday>, Set<PublicHoliday>>? = null

    private var filterBehavior: FilterBehavior? = null

    fun fetchHolidays(firstCountryCode: String, secondCountryCode: String) {
        viewModelScope.launch(errorHandler) {
            withContext(contextProvider.IO) {
                val firstCountryHolidays =
                    async { holidaysUseCase.execute(null, firstCountryCode) }.await()
                val secondCountryHolidays =
                    async { holidaysUseCase.execute(null, secondCountryCode) }.await()

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

    fun resetStates() {
        savedStateHandle[KEY_FILTERED_HOLIDAYS] = listOf<PublicHoliday>()
    }

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            when (throwable) {
                is HttpException -> {
                    _errorState.emit(Status.byStatusCode(throwable.code()).message)
                }

                else -> {
                    _errorState.emit(Status.ERROR_GENERAL.message)
                }
            }
        }
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