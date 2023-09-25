package com.mewz.themoviebooking.delegates

import com.mewz.themoviebooking.data.vos.ticket.TicketInformation

interface TicketViewHolderDelegate {
    fun onTapTicket(ticketData: TicketInformation?)
}