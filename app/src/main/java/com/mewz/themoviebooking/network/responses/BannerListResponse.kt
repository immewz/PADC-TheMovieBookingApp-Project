package com.mewz.themoviebooking.network.responses

import com.google.gson.annotations.SerializedName
import com.mewz.themoviebooking.data.vos.movie.BannerVO

data class BannerListResponse(

    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<BannerVO>?
)
