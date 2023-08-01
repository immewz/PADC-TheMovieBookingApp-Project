package com.mewz.mymoviebookingapp.data.vos.movie

import com.google.gson.annotations.SerializedName

data class SeatVO(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("seat_name")
    val seatName: String?,

    @SerializedName("symbol")
    val symbol: String?,

    @SerializedName("price")
    val price: Int?,

    var selectedSeat:Boolean = false
)
