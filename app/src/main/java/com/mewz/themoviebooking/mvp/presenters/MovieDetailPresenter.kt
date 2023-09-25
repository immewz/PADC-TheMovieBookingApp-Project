package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.mvp.views.MovieDetailView
import com.mewz.themoviebooking.mvp.views.MovieView

interface MovieDetailPresenter: BasePresenter<MovieDetailView> {
    fun onTapBackButton()
    fun onTapBookingButton(movieId: String, movieName: String)
}