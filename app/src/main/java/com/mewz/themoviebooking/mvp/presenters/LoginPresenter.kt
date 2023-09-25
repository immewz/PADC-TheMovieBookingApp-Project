package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.mvp.views.LoginView

interface LoginPresenter: BasePresenter<LoginView> {
    fun onTapVerifyPhoneNumber(phone: String, message: String)
}