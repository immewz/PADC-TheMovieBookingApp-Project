package com.mewz.themoviebooking.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.mvp.presenters.AbstractBasePresenter
import com.mewz.themoviebooking.mvp.presenters.TicketPresenter
import com.mewz.themoviebooking.mvp.views.TicketView

class TicketPresenterImpl: AbstractBasePresenter<TicketView>(), TicketPresenter {

    override fun onUiReady(context: Context, owner: LifecycleOwner) {}

    override fun onTapTicket(ticketData: TicketInformation?) {
        mView.navigateToTicketCancel(ticketData)
    }
}