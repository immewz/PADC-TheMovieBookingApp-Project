package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.ProfilePresenter
import com.mewz.themoviebooking.mvp.views.ProfileView

class ProfilePresenterImpl: AbstractBasePresenter<ProfileView>(), ProfilePresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapLogout() {
        mView.navigateToLoginScreen()
    }
}