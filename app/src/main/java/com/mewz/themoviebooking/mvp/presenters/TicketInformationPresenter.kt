package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.mvp.views.TicketInformationView

interface TicketInformationPresenter: BasePresenter<TicketInformationView> {
    fun onTapBackButton()
    fun onTapDoneButton()
}