package com.mewz.mymoviebookingapp.ui.delgates

interface CinemaDetailViewHolderDelegate {
    fun onTapSeeDetail(cinemaId: Int)
    fun onTapDateCard(cinemaDate: String)
    fun onTapTimeslot(cinemaTimeslotId: Int,cinemaTime: String)
    fun getCinemaName(cinemaName: String?)
    fun getCinemaId(cinemaId: Int?)

    fun onClickCinema(cinemaId: Int?)
}