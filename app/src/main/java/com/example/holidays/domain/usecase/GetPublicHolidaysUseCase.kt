package com.example.holidays.domain.usecase

import com.example.holidays.data.repository.HolidaysRepo
import com.example.holidays.domain.model.PublicHolidays
import java.time.LocalDate
import javax.inject.Inject

class GetPublicHolidaysUseCase @Inject constructor(
    private val repo: HolidaysRepo
) {
    suspend fun execute(year: Int, countryCode: String): Pair<Int, List<PublicHolidays>> {
        val response = repo.fetchHolidaysByYear(year, countryCode)

        return response.status to response.holidays.map {
            PublicHolidays(
                it.name,
                LocalDate.parse(it.date)
            )
        }
    }
}
