package com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime

import com.google.gson.annotations.SerializedName

data class CinemaTimeslotColorVO(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("color")
    val color: String?
)
