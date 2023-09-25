package com.mewz.themoviebooking.network.responses

import com.google.gson.annotations.SerializedName
import com.mewz.themoviebooking.data.vos.cinema.SeatVO

data class SeatListResponse(

    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: MutableList<MutableList<SeatVO>>?
)
