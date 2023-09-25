package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.ChooseTimeAndCinemaView

interface ChooseTimeAndCinemaPresenter: BasePresenter<ChooseTimeAndCinemaView>, CinemaViewHolderDelegate {

    fun onTapBackButton()
}