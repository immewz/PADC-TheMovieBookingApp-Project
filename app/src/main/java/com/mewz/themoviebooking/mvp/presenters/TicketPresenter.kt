package com.mewz.themoviebooking.mvp.presenters

import com.mewz.themoviebooking.delegates.TicketViewHolderDelegate
import com.mewz.themoviebooking.mvp.views.TicketView

interface TicketPresenter: BasePresenter<TicketView>, TicketViewHolderDelegate {

}