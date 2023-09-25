package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.ChooseSeatPresenter
import com.mewz.themoviebooking.mvp.views.ChooseSeatView
import com.mewz.themoviebooking.utils.data.CinemaData
import com.mewz.themoviebooking.utils.data.SeatData

class ChooseSeatPresenterImpl: AbstractBasePresenter<ChooseSeatView>(), ChooseSeatPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapSeat(seatName: String, isAvailable: Boolean?) {
        mView.showSelectedSeat(seatName, isAvailable)
    }

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapBuyTicket(
        movieId: String,
        movieName: String,
        cinemaInfo: CinemaData?,
        seatInfo: SeatData?
    ) {
        mView.navigateToChooseSnackScreen(movieId, movieName, cinemaInfo, seatInfo)
    }
}