package com.example.holidays.data.repository

import com.example.holidays.data.datasource.HolidayService
import com.example.holidays.data.model.AvailableCountriesResponseSchema
import com.example.holidays.data.model.PublicHolidaysResponseSchema
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

internal class HolidaysRepoImplTest {

    @MockK private lateinit var service: HolidayService

    private lateinit var sut: HolidaysRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = HolidaysRepoImpl(service)
    }

    @Test
    fun `when fetchCountries is called, should return a country schema`() = runTest {
        val mockSchema = mockk<AvailableCountriesResponseSchema>()
        coEvery {
            service.fetchCountries()
        } returns mockSchema

        val result = sut.fetchCountries()

        assert(result == mockSchema)
    }

    @Test
    fun `when fetchHolidaysByYear is called, should return a holiday schema`() = runTest {
        val mockSchema = mockk<PublicHolidaysResponseSchema>()
        coEvery {
            service.fetchHolidaysByYear(any(), any())
        } returns mockSchema

        val result = sut.fetchHolidaysByYear(2022, "RS")

        assert(result == mockSchema)
    }
}