package com.mewz.themoviebooking.mvp.views

interface MovieDetailView: BaseView {
    fun navigateToBackScreen()
    fun navigateToChooseTimeAndCinemaScreen(movieId: String, movieName: String)
}