package com.catnip.mycoin.ui.register

import android.app.Dialog
import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData

interface RegisterContract {
    interface View {
        fun initView()
        fun initViewModel()
        fun setClickListener()
        fun checkFormValidation(): Boolean
        fun showLoading(dialog: Dialog, isLoading: Boolean)
        fun showToast(isSuccess: Boolean, msg: String)
    }

    interface ViewModel {
        fun getRegisterResponseLiveData() : LiveData<Resource<UserData>>
        fun registerUser(registerRequest: AuthRequest)
    }

    interface Repository {
        suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String>
    }
}