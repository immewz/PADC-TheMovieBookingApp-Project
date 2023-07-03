package com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime

import com.google.gson.annotations.SerializedName

data class CinemaVO(
    @SerializedName("cinema_id")
    val cinemaId: Int?,

    @SerializedName("cinema")
    val cinema: String?,

    @SerializedName("timeslots")
    val timeslots: List<CinemaTimeslotVO>?,

    var isClicked: Boolean = false
)
