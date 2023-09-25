package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.ChooseTimeAndCinemaPresenter
import com.mewz.themoviebooking.mvp.views.ChooseTimeAndCinemaView

class ChooseTimeAndCinemaPresenterImpl: AbstractBasePresenter<ChooseTimeAndCinemaView>(), ChooseTimeAndCinemaPresenter  {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapSeeDetailCinema(cinemaId: Int?) {
        if (cinemaId != null) {
            mView.navigateToCinemaInfoScreen(cinemaId)
        }
    }

    override fun onTapDateCardCinema(date: String) {
        mView.showDateCard(date)
    }

    override fun onTapTimeslotCinema(timeslotId: Int?, cinemaTime: String?) {
        mView.navigateToSeatScreen(timeslotId, cinemaTime)
    }

    override fun getCinemaId(cinemaId: Int?) {
        mView.showCinemaId(cinemaId)
    }

    override fun getCinemaName(cinemaName: String?) {
        mView.showCinemaName(cinemaName)
    }
}