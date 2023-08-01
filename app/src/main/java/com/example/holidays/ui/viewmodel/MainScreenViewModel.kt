package com.example.holidays.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.holidays.domain.model.AvailableCountry
import com.example.holidays.domain.usecase.FetchCountriesUseCase
import com.example.holidays.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countriesUseCase: FetchCountriesUseCase
) : ViewModel() {

    val countriesBindings =
        savedStateHandle.getStateFlow(KEY_COUNTRIES, listOf<String>())

    /*Ideally this would be stored in database earlier, in this case we store it in VM*/
    private var countriesData: List<AvailableCountry>? = null

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            val countriesResponse: Pair<Status?, List<AvailableCountry>>

            withContext(Dispatchers.IO) {
                countriesResponse = countriesUseCase.execute()
            }

            if (countriesResponse.first == Status.OK) {
                countriesData = countriesResponse.second

                val bindings = countriesResponse.second.map { it.name }
                savedStateHandle[KEY_COUNTRIES] = bindings
            } else {
                // TODO: error handling
            }
        }
    }

    fun getCountryCode(name: String) = countriesData?.find {
        it.name == name
    }?.countryCode

    companion object {
        private const val KEY_COUNTRIES = "ssh_countries"
    }
}