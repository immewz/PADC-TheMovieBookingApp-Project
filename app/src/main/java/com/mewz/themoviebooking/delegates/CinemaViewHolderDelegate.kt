package com.mewz.themoviebooking.delegates

interface CinemaViewHolderDelegate {
    fun onTapSeeDetailCinema(cinemaId: Int?)
    fun onTapDateCardCinema(date: String)
    fun onTapTimeslotCinema(timeslotId: Int?, cinemaTime: String?)
    fun getCinemaId(cinemaId: Int?)
    fun getCinemaName(cinemaName: String?)
}