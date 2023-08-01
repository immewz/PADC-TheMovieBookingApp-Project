package com.mewz.mymoviebookingapp.ui.delgates

interface SeatViewHolderDelegate {
    fun onTapSeat(seatName: String,seatPrice: Int ,isAvailable:Boolean?)
}