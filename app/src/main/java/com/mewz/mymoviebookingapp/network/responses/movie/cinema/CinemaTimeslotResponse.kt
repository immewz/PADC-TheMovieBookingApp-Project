package com.mewz.mymoviebookingapp.network.responses.movie.cinema

import com.google.gson.annotations.SerializedName
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaVO

data class CinemaTimeslotResponse(
    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<CinemaVO>?
)
