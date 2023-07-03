package com.mewz.mymoviebookingapp.ui.utils

import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackVO

data class TicketData(
    val movieInfo: MovieData?,
    val cinemaInfo: CinemaData?,
    val seatInfo: SeatData?,
    val snackPrice: Long?,
    val snackList: List<SnackVO>?
):java.io.Serializable
