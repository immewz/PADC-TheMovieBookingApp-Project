package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.MovieDetailPresenter
import com.mewz.themoviebooking.mvp.views.MovieDetailView
import com.mewz.themoviebooking.mvp.views.MovieView

class MovieDetailPresenterImpl: AbstractBasePresenter<MovieDetailView>(), MovieDetailPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapBookingButton(movieId: String, movieName: String) {
        mView.navigateToChooseTimeAndCinemaScreen(movieId, movieName)
    }

}