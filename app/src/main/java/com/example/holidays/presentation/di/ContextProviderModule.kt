package com.example.holidays.presentation.di

import com.example.holidays.util.ContextProvider
import com.example.holidays.util.CoroutineContextProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface ContextProviderModule {

    @Binds
    @ViewModelScoped
    fun providesContextProvider(provider: CoroutineContextProvider): ContextProvider
}