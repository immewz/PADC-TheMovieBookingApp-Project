package com.mewz.themoviebooking.data.vos.cinema

import com.google.gson.annotations.SerializedName

data class CinemaVO(

    @SerializedName("cinema_id")
    val cinemaId: Int?,

    @SerializedName("cinema")
    val cinema: String?,

    @SerializedName("timeslots")
    val timeslots: List<TimeslotVO>?
)
