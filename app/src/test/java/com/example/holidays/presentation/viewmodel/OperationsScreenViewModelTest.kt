package com.example.holidays.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.CoroutineTestRule
import com.example.TestCoroutineContextProvider
import com.example.holidays.domain.model.PublicHoliday
import com.example.holidays.domain.usecase.FetchPublicHolidaysUseCase
import com.example.holidays.presentation.behavior.FilterBehavior
import com.example.holidays.util.enums.Operations
import com.example.holidays.util.enums.Status
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class OperationsScreenViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val savedStateHandle = SavedStateHandle()
    private val testCoroutineContextProvider = TestCoroutineContextProvider()
    @MockK private lateinit var holidaysUseCase: FetchPublicHolidaysUseCase

    private lateinit var sut: OperationsScreenViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = OperationsScreenViewModel(savedStateHandle, holidaysUseCase, testCoroutineContextProvider)
    }

    @Test
    fun `fetchHolidays sets holidaysPair and filters`() = runTest {
        // Given
        val firstCountryCode = "US"
        val secondCountryCode = "CA"
        val holidaysResponse1 = setOf(PublicHoliday("Holiday 1", "2023-01-01"))
        val holidaysResponse2 = setOf(PublicHoliday("Holiday 2", "2023-12-25"))
        coEvery { holidaysUseCase.execute(null, firstCountryCode) } returns holidaysResponse1
        coEvery { holidaysUseCase.execute(null, secondCountryCode) } returns holidaysResponse2

        // When
        sut.fetchHolidays(firstCountryCode, secondCountryCode)

        // Then
        val holidaysPair = sut.holidaysPair
        assertEquals(holidaysResponse1, holidaysPair?.first)
        assertEquals(holidaysResponse2, holidaysPair?.second)
    }

    @Test
    fun `filter sets filtered holidays`() = runTest {
        // Given
        val expectedHoliday = PublicHoliday("Holiday 1", "2023-01-01")
        val holidaysPair = Pair(
            setOf(expectedHoliday),
            setOf(expectedHoliday)
        )
        sut.holidaysPair = holidaysPair

        // When
        sut.filter(Operations.Common)

        // Then
        assertEquals(listOf(expectedHoliday), savedStateHandle[KEY_FILTERED_HOLIDAYS])
    }

    @Test
    fun `resetStates sets filtered holidays to empty list`() = runTest {
        // Given
        savedStateHandle[KEY_FILTERED_HOLIDAYS] = listOf(PublicHoliday("New Year's Eve", "31st Dec"))

        // When
        sut.resetStates()

        // Then
        assertEquals(savedStateHandle[KEY_FILTERED_HOLIDAYS],  emptyList<PublicHoliday>())
    }

    @Test
    fun `error handling emits error state`() = runTest {
        // Given
        Dispatchers.setMain(StandardTestDispatcher())
        val httpException = mockk<HttpException> {
            every { code() } returns 500
            every { cause } returns Throwable()
        }
        coEvery { holidaysUseCase.execute(any(), any()) } throws httpException

        // When
        sut.fetchHolidays("US", "CA")

        // Then
        val errorState = sut.errorState.first()
        assertEquals(Status.byStatusCode(httpException.code()).message, errorState)
        Dispatchers.resetMain()
    }

    companion object {
        private const val KEY_FILTERED_HOLIDAYS = "ssh_holidays"
    }
}