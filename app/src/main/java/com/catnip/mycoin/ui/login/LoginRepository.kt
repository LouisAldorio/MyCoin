package com.catnip.mycoin.ui.login

import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val authApiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) : LoginContract.Repository {
    override fun saveSession(authToken: String) {
        localDataSource.setAuthToken(authToken)
    }

    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiDataSource.postLoginUser(loginRequest)
    }
}