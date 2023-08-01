package com.mewz.mymoviebookingapp.network.responses.movie

import com.google.gson.annotations.SerializedName
import com.mewz.mymoviebookingapp.data.vos.movie.SeatVO

data class SeatListResponse(
    @SerializedName("code")
    val code: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: MutableList<MutableList<SeatVO>>?
)
