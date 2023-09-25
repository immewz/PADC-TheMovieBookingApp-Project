package com.mewz.themoviebooking.mvp.views

interface CheckoutView: BaseView {
    fun showSnackList()
    fun showDeleteSnack(snackId: Int)
    fun showDialogTicketCancellationPolicy()
    fun navigateToBackScreen()
    fun navigateToPaymentScreen()
    fun navigateToPreviousScreen()
}