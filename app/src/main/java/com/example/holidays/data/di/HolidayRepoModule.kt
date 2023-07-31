package com.example.holidays.data.di

import com.example.holidays.data.repository.HolidaysRepo
import com.example.holidays.data.repository.HolidaysRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HolidayRepoModule {

    @Singleton
    @Binds
    fun bindsHolidayRepo(repo: HolidaysRepoImpl): HolidaysRepo
}