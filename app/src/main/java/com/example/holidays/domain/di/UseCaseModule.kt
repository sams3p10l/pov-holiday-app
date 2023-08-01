package com.example.holidays.domain.di

import com.example.holidays.domain.usecase.FetchCountriesUseCase
import com.example.holidays.domain.usecase.FetchCountriesUseCaseImpl
import com.example.holidays.domain.usecase.FetchPublicHolidaysUseCase
import com.example.holidays.domain.usecase.FetchPublicHolidaysUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    @ViewModelScoped
    fun bindsHolidaysUseCaseRepo(useCase: FetchPublicHolidaysUseCaseImpl): FetchPublicHolidaysUseCase

    @Binds
    @ViewModelScoped
    fun bindsCountriesUseCaseRepo(useCase: FetchCountriesUseCaseImpl): FetchCountriesUseCase
}