package com.mewz.themoviebooking.mvp.views

import com.mewz.themoviebooking.utils.data.CinemaData
import com.mewz.themoviebooking.utils.data.SeatData

interface ChooseSeatView: BaseView {
    fun showSelectedSeat(seatName: String, isAvailable: Boolean?)
    fun navigateToBackScreen()
    fun navigateToChooseSnackScreen(movieId: String, movieName: String, cinemaInfo: CinemaData?, seatInfo: SeatData?)
}