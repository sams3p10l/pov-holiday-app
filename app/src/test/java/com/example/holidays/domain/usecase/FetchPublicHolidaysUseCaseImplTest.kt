package com.example.holidays.domain.usecase

import com.example.holidays.data.model.PublicHolidaysResponseSchema
import com.example.holidays.data.repository.HolidaysRepo
import com.example.holidays.domain.model.PublicHoliday
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchPublicHolidaysUseCaseImplTest {

    @MockK
    private lateinit var repo: HolidaysRepo

    private lateinit var sut: FetchPublicHolidaysUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = FetchPublicHolidaysUseCaseImpl(repo)
    }

    @Test
    fun `when execute called should return expected formatted result`() = runTest {
        // Given
        val year = 2022
        val countryCode = "US"
        val response = mockk<PublicHolidaysResponseSchema>()
        every { response.status } returns 200
        every { response.holidays } returns listOf(
            mockk { every { name } returns "New Year's Day"; every { date } returns "2022-01-01" },
            mockk { every { name } returns "Independence Day"; every { date } returns "2022-07-04" }
        )
        coEvery { repo.fetchHolidaysByYear(year, countryCode) } returns response

        // When
        val result = sut.execute(year, countryCode)

        // Then
        val expected = setOf(
            PublicHoliday("New Year's Day", "Sat, 1 Jan"),
            PublicHoliday("Independence Day", "Mon, 4 Jul")
        )
        assertEquals(expected, result)
    }
}
