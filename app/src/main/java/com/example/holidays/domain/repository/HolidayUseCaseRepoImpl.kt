package com.example.holidays.domain.repository

import com.example.holidays.domain.model.AvailableCountries
import com.example.holidays.domain.usecase.FetchCountriesUseCase
import com.example.holidays.domain.usecase.GetPublicHolidaysUseCase
import javax.inject.Inject

class HolidayUseCaseRepoImpl @Inject constructor(
    private val fetchCountriesUseCase: FetchCountriesUseCase,
    private val getPublicHolidaysUseCase: GetPublicHolidaysUseCase,
) : HolidaysUseCaseRepo {
    override suspend fun fetchCountries(): Pair<Int, List<AvailableCountries>> =
        fetchCountriesUseCase.execute()

    override suspend fun fetchHolidaysByYear(year: Int, countryCode: String) =
        getPublicHolidaysUseCase.execute(year, countryCode)
}