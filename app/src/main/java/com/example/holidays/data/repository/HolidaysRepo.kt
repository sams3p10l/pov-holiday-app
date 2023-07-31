package com.example.holidays.data.repository

import com.example.holidays.data.model.AvailableCountriesResponseSchema
import com.example.holidays.data.model.PublicHolidaysResponseSchema

interface HolidaysRepo {
    suspend fun fetchCountries(): AvailableCountriesResponseSchema
    suspend fun fetchHolidaysByYear(year: Int, countryCode: String): PublicHolidaysResponseSchema
}