package com.example.holidays.domain.usecase

import com.CoroutineTestRule
import com.example.holidays.data.model.AvailableCountriesResponseSchema
import com.example.holidays.data.repository.HolidaysRepo
import com.example.holidays.domain.model.AvailableCountry
import com.example.holidays.util.enums.Status
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchCountriesUseCaseImplTest {

    @MockK
    private lateinit var repo: HolidaysRepo

    private lateinit var sut: FetchCountriesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = FetchCountriesUseCaseImpl(repo)
    }

    @Test
    fun `when execute called should return expected formatted result`() = runTest {
        // Given
        val response = mockk<AvailableCountriesResponseSchema>()
        every { response.status } returns 200
        every { response.countries } returns listOf(
            mockk { every { name } returns "United States"; every { codes.alpha2 } returns "US" },
            mockk { every { name } returns "United Kingdom"; every { codes.alpha2 } returns "UK" }
        )
        coEvery { repo.fetchCountries() } returns response

        // When
        val result = sut.execute()

        // Then
        val expected = listOf(
            AvailableCountry("United States", "US"),
            AvailableCountry("United Kingdom", "UK")
        )

        assertEquals(expected, result)
    }
}
