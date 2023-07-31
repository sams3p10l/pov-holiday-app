package com.example.holidays.domain.usecase

import com.example.holidays.data.repository.HolidaysRepo
import com.example.holidays.domain.model.AvailableCountries
import javax.inject.Inject

class FetchCountriesUseCase @Inject constructor(
    private val repo: HolidaysRepo
) {
    suspend fun execute(): Pair<Int, List<AvailableCountries>> {
        val response = repo.fetchCountries()

        return response.status to response.countries.map {
            AvailableCountries(
                it.name,
                it.code
            )
        }
    }
}