package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.SnackTicketViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.CheckoutView

interface CheckoutPresenter: BasePresenter<CheckoutView>, SnackTicketViewHolderDelegate {
    fun onTapBackButton()
    fun onTapSnackDownButton()
    fun onTapTicketPolicyButton()
    fun onTapContinueButton()
    fun onTapCancelBookingButton()
}