package com.catnip.mycoin.ui.coindetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoinDetailViewModel  @Inject constructor(private val repository: CoinDetailRepository) : ViewModel(), CoinDetailContract.ViewModel {

    private val coinDetailResponseLiveData = MutableLiveData<Resource<CoinDetailResponse>>()

    override fun getCoinDetailLiveData(): LiveData<Resource<CoinDetailResponse>> {
        return coinDetailResponseLiveData
    }

    override fun getCoinDetail(name : String) {
        coinDetailResponseLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCoinDetail(name)
                Log.i("API", response.toString())
                viewModelScope.launch(Dispatchers.Main) {
                    coinDetailResponseLiveData.value = Resource.Success(response)
                }
            }catch (e : Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    coinDetailResponseLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}