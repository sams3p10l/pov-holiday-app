package com.example.holidays.data.di

import com.example.holidays.data.datasource.HolidayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HolidayServiceModule {

    @Provides
    @Singleton
    fun provideHolidayService(retrofit: Retrofit): HolidayService =
        retrofit.create(HolidayService::class.java)
}