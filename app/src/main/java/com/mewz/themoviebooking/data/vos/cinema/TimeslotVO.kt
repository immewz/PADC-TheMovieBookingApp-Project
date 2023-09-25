package com.mewz.themoviebooking.data.vos.cinema

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TimeslotVO(

    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeslotId: Int?,

    @SerializedName("start_time")
    val startTime: String?,

    @SerializedName("status")
    val status: Int?
): Serializable
