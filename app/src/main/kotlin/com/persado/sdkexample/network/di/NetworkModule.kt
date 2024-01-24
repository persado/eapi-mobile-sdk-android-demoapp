package com.persado.sdkexample.network.di

import android.content.Context
import com.persado.sdkexample.network.PersadoManager
import com.persado.sdkexample.network.datasource.FakeAssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesFakeAssetManager(
        @ApplicationContext context: Context,
    ): FakeAssetManager = FakeAssetManager(context.assets::open)


    @Singleton
    @Provides
    fun providesPersadoManager(
        @ApplicationContext context: Context,
        json: Json
    ): PersadoManager {
        return PersadoManager(context, json)
    }
}
