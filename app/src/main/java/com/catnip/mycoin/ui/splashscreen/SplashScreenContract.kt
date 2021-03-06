package com.catnip.mycoin.ui.splashscreen

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData
import com.catnip.mycoin.data.network.model.response.coin.Coin

interface SplashScreenContract {

    interface View {
        fun initView()
        fun initViewModel()
        fun checkLoginStatus()
        fun deleteSession()
        fun navigateToLogin()
        fun navigateToHome()
    }

    interface ViewModel {
        fun getSyncUserLiveData() : LiveData<Resource<UserData>>
        fun isUserLoggedIn() : Boolean
        fun clearSession()
        fun getSyncUser()
    }

    interface Repository {
        fun isUserLoggedIn() : Boolean
        fun clearSession()
        suspend fun getSyncUser() : BaseAuthResponse<UserData, String>
    }
}