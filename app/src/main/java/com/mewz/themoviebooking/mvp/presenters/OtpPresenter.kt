package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.mvp.views.OtpView

interface OtpPresenter: BasePresenter<OtpView>{
    fun onTapConfirmOTP(otpCode: String, token: String)
    fun onTapBack()
}