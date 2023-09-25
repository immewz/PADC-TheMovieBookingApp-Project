package com.mewz.themoviebooking.mvp.views

interface LoginView: BaseView {
    fun displayLoginSentence(title: String)
    fun navigateToOTPScreen(phone: String, message: String)


}