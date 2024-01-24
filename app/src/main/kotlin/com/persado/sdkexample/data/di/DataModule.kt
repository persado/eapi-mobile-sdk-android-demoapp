package com.persado.sdkexample.data.di

import com.persado.sdkexample.data.repository.FakeDataRepository
import com.persado.sdkexample.data.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsDataRepository(
        dataRepository: FakeDataRepository
    ): DataRepository

}