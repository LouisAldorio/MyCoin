package com.catnip.mycoin.data.network.datasource.auth

import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApiDataSource {

    suspend fun postLoginUser(loginRequest: AuthRequest) : BaseAuthResponse<UserData, String>

    suspend fun postRegisterUser(registerRequest: AuthRequest) : BaseAuthResponse<UserData, String>

    suspend fun getSyncUser() : BaseAuthResponse<UserData, String>

    suspend fun getUserData() : BaseAuthResponse<UserData, String>

    suspend fun putUserData(username : String, email : String) : BaseAuthResponse<UserData, String>
}