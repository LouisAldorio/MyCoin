package com.catnip.mycoin.data.network.model.request.auth

import com.google.gson.annotations.SerializedName

data class AuthRequest(

    @SerializedName("username")
    val username : String? = null,

    @SerializedName("email")
    val email : String? = null,

    @SerializedName("password")
    val password : String? = null,

)
