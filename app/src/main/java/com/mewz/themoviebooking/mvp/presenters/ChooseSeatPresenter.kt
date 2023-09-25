package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.SeatViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.ChooseSeatView
import com.mewz.themoviebooking.utils.data.CinemaData
import com.mewz.themoviebooking.utils.data.SeatData

interface ChooseSeatPresenter: BasePresenter<ChooseSeatView>, SeatViewHolderDelegate {
    fun onTapBackButton()
    fun onTapBuyTicket(movieId: String, movieName: String, cinemaInfo: CinemaData?, seatInfo: SeatData?)
}