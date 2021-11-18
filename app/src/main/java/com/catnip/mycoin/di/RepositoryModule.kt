package com.catnip.mycoin.di

import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinApiDataSource
import com.catnip.mycoin.ui.coinlist.CoinListRepository
import com.catnip.mycoin.ui.splashscreen.SplashScreenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoinListRepository(dataSource: CoinApiDataSource) : CoinListRepository {
        return CoinListRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideSplashScreenRepository(authApiDataSource: AuthApiDataSource
                                      , localDataSource : LocalDataSource) : SplashScreenRepository {
        return SplashScreenRepository(authApiDataSource, localDataSource)
    }
}