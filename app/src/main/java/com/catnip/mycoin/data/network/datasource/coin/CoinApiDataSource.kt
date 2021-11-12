package com.catnip.mycoin.data.network.datasource.coin

import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse

interface CoinApiDataSource {

    suspend fun getCoinList() : List<Coin>

    suspend fun getCoinDetail(name : String) : CoinDetailResponse

}