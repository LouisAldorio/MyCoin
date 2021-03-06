package com.catnip.mycoin.ui.register

import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData
import javax.inject.Inject

class RegisterRepository
@Inject constructor(
    private val authApiDataSource: AuthApiDataSource
) : RegisterContract.Repository {
    override suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiDataSource.postRegisterUser(registerRequest)
    }
}