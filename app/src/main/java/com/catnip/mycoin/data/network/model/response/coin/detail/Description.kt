package com.catnip.mycoin.data.network.model.response.coin.detail

import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("en")
    val en: String?
)
