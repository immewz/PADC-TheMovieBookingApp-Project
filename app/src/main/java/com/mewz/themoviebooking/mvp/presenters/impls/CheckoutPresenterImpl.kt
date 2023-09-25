package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.CheckoutPresenter
import com.mewz.themoviebooking.mvp.views.CheckoutView

class CheckoutPresenterImpl: AbstractBasePresenter<CheckoutView>(), CheckoutPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapSnackDownButton() {
        mView.showSnackList()
    }

    override fun onTapSnack(snackId: Int) {
        mView.showDeleteSnack(snackId)
    }

    override fun onTapTicketPolicyButton() {
        mView.showDialogTicketCancellationPolicy()
    }

    override fun onTapContinueButton() {
        mView.navigateToPaymentScreen()
    }

    override fun onTapCancelBookingButton() {
        mView.navigateToPreviousScreen()
    }


}