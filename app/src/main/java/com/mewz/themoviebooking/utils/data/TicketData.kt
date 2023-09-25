package com.mewz.themoviebooking.utils.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class TicketData(
    val movieId: String?,
    val movieName: String?,
    val cinemaInfo: CinemaData?,
    val seatInfo: SeatData?,
    val snackPrice: String?,
    val snackList: List<SnackVO>?
): Serializable
