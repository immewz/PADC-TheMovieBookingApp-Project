package com.mewz.themoviebooking.mvp.views

interface CinemaView: BaseView {
    fun showCinemaId(cinemaId: Int?)
    fun showCinemaName(cinemaName: String?)
    fun navigateToMainScreen()
    fun navigateToSeatScreen(timeslotId: Int?, cinemaTime: String?)
    fun navigateToCinemaDetailScreen(cinemaId: Int?)
}