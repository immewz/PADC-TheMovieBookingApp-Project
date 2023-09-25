package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.MoviePresenter
import com.mewz.themoviebooking.mvp.views.MovieView

class MoviePresenterImpl: AbstractBasePresenter<MovieView>(), MoviePresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapMovie(movieId: Int) {
        mView.navigateToMovieDetailScreen(movieId)
    }
}