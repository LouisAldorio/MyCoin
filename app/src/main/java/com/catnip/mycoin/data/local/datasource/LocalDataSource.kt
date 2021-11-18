package com.catnip.mycoin.data.local.datasource

interface LocalDataSource {
    fun getAuthToken() : String?
    fun setAuthToken(authToken : String?)
    fun isUserLoggedIn() : Boolean
    fun deleteSession()
}