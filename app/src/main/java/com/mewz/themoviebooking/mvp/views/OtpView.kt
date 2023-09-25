package com.mewz.themoviebooking.mvp.views

interface OtpView: BaseView {
    fun navigateToLocationScreen(otpCode: String, token: String)
    fun navigateToLoginScreen()
}