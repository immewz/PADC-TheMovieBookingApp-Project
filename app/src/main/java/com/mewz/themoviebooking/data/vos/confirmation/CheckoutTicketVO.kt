package com.mewz.themoviebooking.data.vos.confirmation

import com.google.gson.annotations.SerializedName
import com.mewz.themoviebooking.data.vos.cinema.TimeslotVO
import java.io.Serializable

data class CheckoutTicketVO(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("booking_no")
    val booking_no: String?,

    @SerializedName("booking_date")
    val booking_date: String?,

    @SerializedName("row")
    val row: String?,

    @SerializedName("seat")
    val seat: String?,

    @SerializedName("total_seat")
    val total_seat: Int?,

    @SerializedName("total")
    val total: String?,

    @SerializedName("movie_id")
    val movie_id: Int?,

    @SerializedName("cinema_id")
    val cinema_id: Int?,

    @SerializedName("username")
    val username: String?,

    @SerializedName("timeslot")
    val timeslot: TimeslotVO?,

    @SerializedName("snacks")
    val snacks: List<CheckoutTicketSnackVO>?,

    @SerializedName("qr_code")
    val qrCode: String?,
): Serializable
