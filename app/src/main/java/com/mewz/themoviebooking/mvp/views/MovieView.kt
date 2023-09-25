package com.mewz.themoviebooking.mvp.views

interface MovieView: BaseView {
    fun navigateToMovieDetailScreen(movieId: Int)
}