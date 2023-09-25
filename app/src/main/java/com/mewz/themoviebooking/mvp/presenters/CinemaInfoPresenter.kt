package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.mvp.views.CinemaInfoView

interface CinemaInfoPresenter: BasePresenter<CinemaInfoView> {
    fun onTapBackScreen()
}