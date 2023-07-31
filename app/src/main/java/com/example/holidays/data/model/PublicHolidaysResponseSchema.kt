package com.example.holidays.data.model

import com.squareup.moshi.Json

data class PublicHolidaysResponseSchema(
    val status: Int,
    val requests: RequestsSchema,
    val holidays: List<HolidaysSchema>
) {
    data class RequestsSchema(
        val used: Int,
        val available: Int,
        val resets: String
    )

    data class HolidaysSchema(
        val name: String,
        val date: String,
        val observed: String,
        @field:Json(name = SERIALIZED_PARAM_PUBLIC) val isPublic: Boolean,
        val country: String,
        val uuid: String,
        val weekday: WeekdaySchema
    ) {
        data class WeekdaySchema(
            val date: DateSchema,
            val observed: ObservedDateSchema
        ) {
            data class DateSchema(
                val name: String,
                val numeric: String
            )
            data class ObservedDateSchema(
                val name: String,
                val numeric: String
            )
        }
    }

    companion object {
        private const val SERIALIZED_PARAM_PUBLIC = "public"
    }
}

