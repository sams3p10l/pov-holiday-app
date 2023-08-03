package com.example.holidays.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holidays.domain.model.AvailableCountry
import com.example.holidays.domain.usecase.FetchCountriesUseCase
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
    private val countriesUseCase: FetchCountriesUseCase
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
            val countriesResponse: Pair<Status?, List<AvailableCountry>>

            withContext(Dispatchers.IO) {
                countriesResponse = countriesUseCase.execute()
            }
            countriesData = countriesResponse.second

            val bindings = countriesResponse.second.map { it.name }
            savedStateHandle[KEY_COUNTRIES] = bindings
        }
    }

    fun getCountryCode(name: String) = countriesData?.find {
        it.name == name
    }?.countryCode

    private fun initExceptionHandler() {
        errorHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e(this::class.simpleName, ": ", throwable)

            when (throwable) {
                is HttpException -> {
                    _errorState.tryEmit(Status.byStatusCode(throwable.code()).message)
                }

                else -> {
                    _errorState.tryEmit(Status.ERROR_GENERAL.message)
                }
            }
        }
    }

    companion object {
        private const val KEY_COUNTRIES = "ssh_countries"
    }
}