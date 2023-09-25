package com.mewz.themoviebooking.mvp.views

interface ChooseSnackView: BaseView {
    fun showSnackPlus(snackId: Int)
    fun showSnackMinus(snackId: Int)
    fun showBottomSheetSnack()
    fun navigateToBackScreen()
    fun navigateToCheckoutScreen()
}