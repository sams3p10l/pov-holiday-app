package com.example.holidays.domain.usecase

import com.example.holidays.data.repository.HolidaysRepo
import com.example.holidays.domain.model.PublicHoliday
import java.time.LocalDate
import javax.inject.Inject

class FetchPublicHolidaysUseCaseImpl @Inject constructor(
    private val repo: HolidaysRepo
): FetchPublicHolidaysUseCase {
    override suspend fun execute(year: Int, countryCode: String): Pair<Int, List<PublicHoliday>> {
        val response = repo.fetchHolidaysByYear(year, countryCode)

        return response.status to response.holidays.map {
            PublicHoliday(
                it.name,
                LocalDate.parse(it.date)
            )
        }
    }
}

fun interface FetchPublicHolidaysUseCase {
    suspend fun execute(year: Int, countryCode: String): Pair<Int, List<PublicHoliday>>
}