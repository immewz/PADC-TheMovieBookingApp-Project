package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.LocationPresenter
import com.mewz.themoviebooking.mvp.views.LocationView

class LocationPresenterImpl: AbstractBasePresenter<LocationView>(), LocationPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapCities(city: String) {
        mView.navigateToMainScreen(city)
    }

}