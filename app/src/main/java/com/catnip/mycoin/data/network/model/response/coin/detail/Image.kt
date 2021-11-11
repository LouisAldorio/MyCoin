package com.catnip.mycoin.data.network.model.response.coin.detail

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("large")
    val large: String?,
    @SerializedName("small")
    val small: String?,
    @SerializedName("thumb")
    val thumb: String?
)
