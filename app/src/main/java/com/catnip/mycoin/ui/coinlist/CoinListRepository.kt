package com.catnip.mycoin.ui.coinlist

import android.util.Log
import com.catnip.mycoin.data.network.datasource.coin.CoinApiDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinApiDataSourceImpl
import com.catnip.mycoin.data.network.model.response.coin.Coin
import javax.inject.Inject

class CoinListRepository @Inject constructor(private val dataSource : CoinApiDataSource) : CoinListContract.Repository {

    override suspend fun getCoinList(): List<Coin> {
        return dataSource.getCoinList()
    }
}