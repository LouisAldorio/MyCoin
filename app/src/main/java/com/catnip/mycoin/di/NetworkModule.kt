package com.catnip.mycoin.di

import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.services.AuthApiService
import com.catnip.mycoin.data.network.services.CoinApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthApiServices(localDataSource: LocalDataSource): AuthApiService {
        return AuthApiService.invoke(localDataSource)
    }

    @Singleton
    @Provides
    fun provideCoinGeckoApiServices(): CoinApiService {
        return CoinApiService.invoke()
    }
}