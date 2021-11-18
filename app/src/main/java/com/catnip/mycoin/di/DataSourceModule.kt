package com.catnip.mycoin.di

import com.catnip.mycoin.data.local.SessionPreference
import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.local.datasource.LocalDataSourceImpl
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.mycoin.data.network.datasource.coin.CoinApiDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinApiDataSourceImpl
import com.catnip.mycoin.data.network.services.AuthApiService
import com.catnip.mycoin.data.network.services.CoinApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideAuthDataSource(authApiService: AuthApiService): AuthApiDataSource {
        return AuthApiDataSourceImpl(authApiService)
    }

    @Singleton
    @Provides
    fun provideCoinGeckoDataSource(coinGeckoApiServices: CoinApiService): CoinApiDataSource {
        return CoinApiDataSourceImpl(coinGeckoApiServices)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(sessionPreference: SessionPreference): LocalDataSource {
        return LocalDataSourceImpl(sessionPreference)
    }
}