package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.CinemaInfoPresenter
import com.mewz.themoviebooking.mvp.views.CinemaInfoView

class CinemaInfoPresenterImpl: AbstractBasePresenter<CinemaInfoView>(), CinemaInfoPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapBackScreen() {
        mView.navigateToCinemaScreen()
    }


}