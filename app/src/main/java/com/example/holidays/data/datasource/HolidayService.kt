package com.example.holidays.data.datasource

import com.example.holidays.data.model.AvailableCountriesResponseSchema
import com.example.holidays.data.model.PublicHolidaysResponseSchema
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayService {
    @GET(ENDPOINT_ALL_COUNTRIES)
    suspend fun fetchCountries(): AvailableCountriesResponseSchema

    @GET(ENDPOINT_FETCH_HOLIDAYS)
    suspend fun fetchHolidaysByYear(
        @Query(PARAM_YEAR) year: Int,
        @Query(PARAM_COUNTRY_ALPHA) countryAlpha: String
    ): PublicHolidaysResponseSchema

    companion object {
        private const val ENDPOINT_ALL_COUNTRIES = "countries"
        private const val ENDPOINT_FETCH_HOLIDAYS = "holidays"
        private const val PARAM_YEAR = "year"
        private const val PARAM_COUNTRY_ALPHA = "country"
    }
}