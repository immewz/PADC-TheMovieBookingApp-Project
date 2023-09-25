package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.PaymentPresenter
import com.mewz.themoviebooking.mvp.views.PaymentView

class PaymentPresenterImpl: AbstractBasePresenter<PaymentView>(), PaymentPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapOfferButton() {
        mView.navigateToTicketInformationScreen(0)
    }


    override fun onTapPayment(paymentId: Int) {
        mView.navigateToTicketInformationScreen(paymentId)
    }
}