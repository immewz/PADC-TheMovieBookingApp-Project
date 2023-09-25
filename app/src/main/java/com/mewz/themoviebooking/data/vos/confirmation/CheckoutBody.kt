package com.mewz.themoviebooking.data.vos.confirmation

import com.google.gson.annotations.SerializedName

data class CheckoutBody(

    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeslotId: Int?,

    @SerializedName("seat_number")
    val seatNumber: String?,

    @SerializedName("booking_date")
    val bookingDate: String?,

    @SerializedName("movie_id")
    val movieId: Int?,

    @SerializedName("payment_type_id")
    val paymentTypeId: Int?,

    @SerializedName("snacks")
    val snacks: MutableList<CheckoutSnack>?
)
