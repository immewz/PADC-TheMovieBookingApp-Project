package com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime

import com.google.gson.annotations.SerializedName

data class CinemaTimeslotVO(
    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeslotId: Int?,

    @SerializedName("start_time")
    val startTime: String?,

    @SerializedName("status")
    val status: Int?
)
