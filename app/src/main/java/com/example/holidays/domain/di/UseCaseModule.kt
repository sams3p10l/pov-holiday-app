package com.example.holidays.domain.di

import com.example.holidays.domain.repository.HolidayUseCaseRepoImpl
import com.example.holidays.domain.repository.HolidaysUseCaseRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Singleton
    @Binds
    fun bindsHolidaysUseCaseRepo(repo: HolidayUseCaseRepoImpl): HolidaysUseCaseRepo
}