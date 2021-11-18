package com.catnip.mycoin.ui.login

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData

interface LoginContract {
    interface View {
        fun initView()
        fun initViewModel()
    }

    interface ViewModel {
        fun getLoginResultLiveData(): LiveData<Resource<UserData>>
        fun saveSession(authToken: String)
        fun loginUser(loginRequest: AuthRequest)
    }

    interface Repository {
        fun saveSession(authToken: String)
        suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>
    }
}