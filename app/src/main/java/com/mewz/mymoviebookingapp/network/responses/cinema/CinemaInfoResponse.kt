package com.mewz.mymoviebookingapp.network.responses.cinema

import com.google.gson.annotations.SerializedName
import com.mewz.mymoviebookingapp.data.vos.cinema.CinemaInfoVO

data class CinemaInfoResponse(
    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<CinemaInfoVO>?,

    @SerializedName("latest_time")
    val latest_time: String?
)
