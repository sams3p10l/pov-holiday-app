package com.example.holidays.data.model

import com.squareup.moshi.Json

data class AvailableCountriesResponseSchema(
    val status: Int,
    val requests: RequestsSchema,
    val countries: List<CountriesSchema>
) {
    data class RequestsSchema(
        val used: Int,
        val available: Int,
        val resets: String
    )

    data class CountriesSchema(
        val code: String,
        val name: String,
        val codes: Codes,
        val languages: List<String>,
        val flag: String,
        val subdivisions: List<SubdivisionsSchema>
    ) {
        data class Codes(
            @field:Json(name = SERIALIZED_PARAM_ALPHA_2) val alpha2: String,
            @field:Json(name = SERIALIZED_PARAM_ALPHA_3) val alpha3: String,
            val numeric: String
        )

        data class SubdivisionsSchema(
            val code: String,
            val name: String,
            val languages: List<String>
        )
    }

    companion object {
        private const val SERIALIZED_PARAM_ALPHA_2 = "alpha-2"
        private const val SERIALIZED_PARAM_ALPHA_3 = "alpha-3"
    }
}