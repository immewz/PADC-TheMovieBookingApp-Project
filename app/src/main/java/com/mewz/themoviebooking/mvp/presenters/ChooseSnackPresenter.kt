package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.SnackViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.ChooseSnackView

interface ChooseSnackPresenter: BasePresenter<ChooseSnackView>, SnackViewHolderDelegate {
    fun onTapBackButton()
    fun onTapBottomSheet()
    fun onTapNextScreen()
}