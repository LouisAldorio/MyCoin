package com.catnip.mycoin.ui.coindetail

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse

interface CoinDetailContract {

    interface View {
        fun initView()
        fun showLoading(isLoading : Boolean)
        fun showContent(isContentShown : Boolean)
        fun showErrMsg(isError : Boolean, msg : String?)
        fun observeViewModel()
        fun getDetailData()
    }

    interface ViewModel {
        fun getCoinDetailLiveData() : LiveData<Resource<CoinDetailResponse>>
        fun getCoinDetail(name : String)
    }

    interface Repository {
        suspend fun getCoinDetail(name : String) : CoinDetailResponse
    }
}