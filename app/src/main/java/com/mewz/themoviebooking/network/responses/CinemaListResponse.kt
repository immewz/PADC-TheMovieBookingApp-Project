package com.mewz.themoviebooking.network.responses

import com.google.gson.annotations.SerializedName
import com.mewz.themoviebooking.data.vos.cinema.CinemaInfoVO

data class CinemaListResponse(

    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<CinemaInfoVO>?,

    @SerializedName("latest_time")
    val latestTime: String?
)
