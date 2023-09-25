package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.CinemaView

interface CinemaPresenter: BasePresenter<CinemaView>, CinemaViewHolderDelegate {
    fun onTapBackScreen()

}