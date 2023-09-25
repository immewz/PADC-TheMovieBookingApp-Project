package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.CinemaPresenter
import com.mewz.themoviebooking.mvp.views.CinemaView

class CinemaPresenterImpl: AbstractBasePresenter<CinemaView>(), CinemaPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapBackScreen() {
        mView.navigateToMainScreen()
    }

    override fun onTapSeeDetailCinema(cinemaId: Int?) {
        mView.navigateToCinemaDetailScreen(cinemaId)
    }

    override fun onTapDateCardCinema(date: String) {}

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