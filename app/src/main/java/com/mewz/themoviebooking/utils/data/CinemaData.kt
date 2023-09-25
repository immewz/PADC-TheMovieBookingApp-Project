package com.mewz.themoviebooking.utils.data

import android.os.Parcelable
import java.io.Serializable

data class CinemaData(
    val cinemaName: String?,
    val date: String?,
    val time: String?,
    val address: String?,
    val cinemaTimeslotId: Int?
): Serializable
