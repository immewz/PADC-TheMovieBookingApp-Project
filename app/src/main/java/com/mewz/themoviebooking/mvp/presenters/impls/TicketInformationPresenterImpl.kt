package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.TicketInformationPresenter
import com.mewz.themoviebooking.mvp.views.TicketInformationView

class TicketInformationPresenterImpl: AbstractBasePresenter<TicketInformationView>(),
    TicketInformationPresenter  {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapDoneButton() {
        mView.navigateToMainScreen()
    }


}