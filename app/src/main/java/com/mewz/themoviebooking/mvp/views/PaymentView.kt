package com.mewz.themoviebooking.mvp.views

interface PaymentView: BaseView {
    fun navigateToBackScreen()
    fun navigateToTicketInformationScreen(paymentId: Int)
}