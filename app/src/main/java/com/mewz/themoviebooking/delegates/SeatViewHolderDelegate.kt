package com.mewz.themoviebooking.delegates

import com.mewz.themoviebooking.data.vos.cinema.SeatVO

interface SeatViewHolderDelegate {
    fun onTapSeat(seatName: String, isAvailable: Boolean?)
}