package com.mewz.mymoviebookingapp.network.responses.movie.cinema

import com.google.gson.annotations.SerializedName
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaConfigVO

data class CinemaConfigResponse(
    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<CinemaConfigVO>?
)
