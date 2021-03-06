package com.catnip.mycoin.ui.coinlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.coin.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoinListViewModel @Inject constructor(private val repository: CoinListRepository) : ViewModel(), CoinListContract.ViewModel {

    private val coinListResponseLiveData = MutableLiveData<Resource<List<Coin>>>()

    override fun getCoinListLiveData(): LiveData<Resource<List<Coin>>> = coinListResponseLiveData

    override fun getCoinList() {

        coinListResponseLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCoinList()
                Log.i("API", response.toString())
                viewModelScope.launch(Dispatchers.Main) {
                    coinListResponseLiveData.value = Resource.Success(response)
                }
            }catch (e : Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    coinListResponseLiveData.value = Resource.Error(e.message.orEmpty())
                }

            }
        }
    }


}