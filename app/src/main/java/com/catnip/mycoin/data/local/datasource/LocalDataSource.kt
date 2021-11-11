package com.catnip.mycoin.data.local.datasource

interface LocalDataSource {
    fun getAuthToken() : String?
}