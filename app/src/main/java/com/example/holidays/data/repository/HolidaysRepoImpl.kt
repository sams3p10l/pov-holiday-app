package com.example.holidays.data.repository

import com.example.holidays.data.datasource.HolidayService
import com.example.holidays.data.model.AvailableCountriesResponseSchema
import com.example.holidays.data.model.PublicHolidaysResponseSchema
import javax.inject.Inject

class HolidaysRepoImpl @Inject constructor(
    private val service: HolidayService
) : HolidaysRepo {

    override suspend fun fetchCountries(): AvailableCountriesResponseSchema {
        return service.fetchCountries()
    }

    override suspend fun fetchHolidaysByYear(
        year: Int,
        countryCode: String
    ): PublicHolidaysResponseSchema {
        return service.fetchHolidaysByYear(year, countryCode)
    }
}