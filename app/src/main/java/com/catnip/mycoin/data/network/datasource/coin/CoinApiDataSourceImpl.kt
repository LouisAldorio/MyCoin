package com.catnip.mycoin.data.network.datasource.coin

import android.util.Log
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.mycoin.data.network.services.CoinApiService
import javax.inject.Inject

class CoinApiDataSourceImpl @Inject constructor(private val coinApiService : CoinApiService) : CoinApiDataSource {

    override suspend fun getCoinList(): List<Coin> {
        val result = coinApiService.getCoinList()
        Log.i("API", result.toString())
        return coinApiService.getCoinList()
    }

    override suspend fun getCoinDetail(
        name: String,
    ): CoinDetailResponse {
        return coinApiService.getCoinDetail(name)
    }
}