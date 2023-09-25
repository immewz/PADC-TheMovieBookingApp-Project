package com.mewz.themoviebooking.mvp.views

import com.mewz.themoviebooking.data.vos.ticket.TicketInformation

interface TicketView: BaseView {
    fun navigateToTicketCancel(ticketData: TicketInformation?)
}