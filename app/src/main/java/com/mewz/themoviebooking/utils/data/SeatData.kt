package com.mewz.themoviebooking.utils.data

import java.io.Serializable

data class SeatData(
    val numberOfTicket: Int?,
    val ticketList: MutableList<String>?,
    val ticketTotalPrice: Int?
): Serializable
