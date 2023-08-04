package com.example.holidays.domain.usecase

import com.example.holidays.data.repository.HolidaysRepo
import com.example.holidays.domain.model.AvailableCountry
import com.example.holidays.util.enums.Status
import javax.inject.Inject

class FetchCountriesUseCaseImpl @Inject constructor(
    private val repo: HolidaysRepo
): FetchCountriesUseCase {
    override suspend fun execute(): List<AvailableCountry> {
        val response = repo.fetchCountries()

        return response.countries.map {
            AvailableCountry(
                it.name,
                it.codes.alpha2
            )
        }
    }
}

fun interface FetchCountriesUseCase {
    suspend fun execute(): List<AvailableCountry>
}