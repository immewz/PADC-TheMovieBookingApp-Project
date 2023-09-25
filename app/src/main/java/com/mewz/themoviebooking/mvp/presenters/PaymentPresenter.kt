package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.PaymentViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.PaymentView

interface PaymentPresenter: BasePresenter<PaymentView>, PaymentViewHolderDelegate {
    fun onTapBackButton()
    fun onTapOfferButton()
}