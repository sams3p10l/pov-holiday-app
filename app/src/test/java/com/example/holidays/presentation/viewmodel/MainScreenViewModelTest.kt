package com.example.holidays.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.CoroutineTestRule
import com.example.TestCoroutineContextProvider
import com.example.holidays.domain.model.AvailableCountry
import com.example.holidays.domain.usecase.FetchCountriesUseCase
import com.example.holidays.util.enums.Status
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class MainScreenViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val savedStateHandle = SavedStateHandle()
    private val testCoroutineContextProvider = TestCoroutineContextProvider()

    @MockK(relaxed = true)
    private lateinit var countriesUseCase: FetchCountriesUseCase

    private lateinit var sut: MainScreenViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getCountries success sets state correctly`() = runTest {
        // Given
        val expectedCountries: List<AvailableCountry> = listOf(AvailableCountry("United States", "US"))
        coEvery { countriesUseCase.execute() } returns expectedCountries

        // When
        sut = MainScreenViewModel(savedStateHandle, countriesUseCase, testCoroutineContextProvider)

        // Then
        val countriesBindings = sut.countriesBindings.take(1).toList()[0]
        val country = sut.getCountryCode("United States")

        assertEquals(listOf("United States"), countriesBindings)
        assertEquals("US", country)
        assertEquals(savedStateHandle["ssh_countries"], countriesBindings)
    }

    @Test
    fun `getCountries error emits error state`() = runTest {
        // Given
        Dispatchers.setMain(StandardTestDispatcher())
        val errorMessage = "Network error"
        coEvery { countriesUseCase.execute() } throws mockk<HttpException> {
            every { code() } returns 500
            every { message() } returns errorMessage
            every { cause } returns Throwable()
        }

        // When
        sut = MainScreenViewModel(savedStateHandle, countriesUseCase, testCoroutineContextProvider)

        // Then
        val errorState = sut.errorState.take(1).first()
        assertEquals(Status.byStatusCode(500).message, errorState)
        Dispatchers.resetMain()
    }
}
