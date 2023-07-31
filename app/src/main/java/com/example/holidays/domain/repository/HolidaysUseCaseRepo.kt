package com.example.holidays.domain.repository

import com.example.holidays.domain.model.AvailableCountries
import com.example.holidays.domain.model.PublicHolidays

interface HolidaysUseCaseRepo {
    suspend fun fetchCountries(): Pair<Int, List<AvailableCountries>>
    suspend fun fetchHolidaysByYear(year: Int, countryCode: String): Pair<Int, List<PublicHolidays>>
}