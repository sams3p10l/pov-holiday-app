package com.example.holidays.presentation.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.core.util.toAndroidXPair
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holidays.domain.model.AvailableCountry
import com.example.holidays.domain.usecase.FetchCountriesUseCase
import com.example.holidays.util.ContextProvider
import com.example.holidays.util.CoroutineContextProvider
import com.example.holidays.util.enums.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countriesUseCase: FetchCountriesUseCase,
    private val contextProvider: ContextProvider
) : ViewModel() {

    val countriesBindings =
        savedStateHandle.getStateFlow(KEY_COUNTRIES, listOf<String>())

    private val _errorState: MutableSharedFlow<Int> = MutableSharedFlow()
    val errorState = _errorState.asSharedFlow()

    /*Ideally this would be stored in database earlier, in this case we store it in VM*/
    private var countriesData: List<AvailableCountry>? = null

    private lateinit var errorHandler: CoroutineExceptionHandler

    init {
        initExceptionHandler()
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch(errorHandler) {
            val countriesResponse: List<AvailableCountry>

            withContext(contextProvider.IO) {
                countriesResponse = countriesUseCase.execute().also {
                    countriesData = it
                }
            }

            val bindings = countriesResponse.map { it.name }
            savedStateHandle[KEY_COUNTRIES] = bindings
        }
    }

    fun getCountryCode(name: String) = countriesData?.find {
        it.name == name
    }?.countryCode

    private fun initExceptionHandler() {
        errorHandler = CoroutineExceptionHandler { _, throwable ->
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
    }

    companion object {
        private const val KEY_COUNTRIES = "ssh_countries"
    }
}