package com.catnip.mycoin.ui.coindetail

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.datasource.coin.CoinApiDataSource
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.mycoin.ui.coinlist.CoinListContract
import javax.inject.Inject

class CoinDetailRepository @Inject constructor(private val dataSource : CoinApiDataSource) : CoinDetailContract.Repository {

    override suspend fun getCoinDetail(name : String): CoinDetailResponse {
        return dataSource.getCoinDetail(name)
    }
}