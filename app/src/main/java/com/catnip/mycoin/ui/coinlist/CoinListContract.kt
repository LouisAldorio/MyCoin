package com.catnip.mycoin.ui.coinlist

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.Coin

interface CoinListContract {
    
    interface View {
        fun initView()
        fun showLoading(isLoading : Boolean)
        fun showContent(isContentShown : Boolean)
        fun showErrMsg(isError : Boolean, msg : String?)
        fun observeViewModel()
        fun initList()
        fun getData()
        fun initSwipeRefresh()
    }
    
    interface ViewModel {
        fun getCoinListLiveData() : LiveData<Resource<List<Coin>>>
        fun getCoinList()
    }

    interface Repository {
        suspend fun getCoinList() : List<Coin>
    }
}