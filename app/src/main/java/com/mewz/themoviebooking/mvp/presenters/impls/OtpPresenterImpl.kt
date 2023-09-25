package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.OtpPresenter
import com.mewz.themoviebooking.mvp.views.OtpView

class OtpPresenterImpl: AbstractBasePresenter<OtpView>(), OtpPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapConfirmOTP(otpCode: String, token: String) {
        mView.navigateToLocationScreen(otpCode, token)
    }

    override fun onTapBack() {
        mView.navigateToLoginScreen()
    }
}