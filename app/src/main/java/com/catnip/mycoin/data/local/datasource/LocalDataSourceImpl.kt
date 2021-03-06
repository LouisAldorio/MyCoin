package com.catnip.mycoin.data.local.datasource

import com.catnip.mycoin.data.local.SessionPreference
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val sessionPreference: SessionPreference) : LocalDataSource {

    override fun getAuthToken(): String? {
        return sessionPreference.authToken
    }

    override fun setAuthToken(authToken: String?) {
        sessionPreference.authToken = authToken
    }

    override fun isUserLoggedIn(): Boolean {
        return  !sessionPreference.authToken.isNullOrEmpty()
    }

    override fun deleteSession() {
        sessionPreference.deleteSession()
    }

}