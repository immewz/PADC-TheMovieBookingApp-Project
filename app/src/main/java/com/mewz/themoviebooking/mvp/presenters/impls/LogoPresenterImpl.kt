package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.LogoPresenter
import com.mewz.themoviebooking.mvp.views.LogoView

class LogoPresenterImpl: AbstractBasePresenter<LogoView>(), LogoPresenter {

    private val mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl
    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mTheMovieBookingModel.setUpRemoteConfigWithDefaultValues()
        mTheMovieBookingModel.fetchRemoteConfigs()
    }

}