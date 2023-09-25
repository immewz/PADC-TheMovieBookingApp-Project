package com.mewz.themoviebooking.mvp.views

interface ChooseTimeAndCinemaView: BaseView {
    fun showDateCard(date: String)
    fun showCinemaId(cinemaId: Int?)
    fun showCinemaName(cinemaName: String?)
    fun navigateToBackScreen()
    fun navigateToSeatScreen(timeslotId: Int?, cinemaTime: String?)
    fun navigateToCinemaInfoScreen(cinemaId: Int?)
}