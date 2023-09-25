package com.mewz.themoviebooking.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.databinding.ViewholderTicketBinding
import com.mewz.themoviebooking.delegates.TicketViewHolderDelegate
import com.mewz.themoviebooking.network.IMAGE_BASE_URL

class TicketViewHolder(itemView: View, private var delegate: TicketViewHolderDelegate, private var type: String)
    : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderTicketBinding

    private var mTicket: TicketInformation? = null

    init {
        binding = ViewholderTicketBinding.bind(itemView)

        itemView.setOnClickListener {
            delegate.onTapTicket(mTicket)
        }
    }

    fun bindData(ticket: TicketInformation) {

        mTicket = ticket

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${ticket.moviePoster}")
            .into(binding.ivMoviePoster)

        binding.tvMovieName.text = ticket.movieName
        binding.tvCinemaName.text = ticket.cinemaName
        binding.tvTicketCount.text = ticket.ticketCheckout?.total_seat.toString()
        binding.tvSeat.text = ticket.ticketCheckout?.seat

        binding.tvDate.text = ticket.ticketCheckout?.booking_date
        binding.tvTime.text = ticket.ticketCheckout?.timeslot?.startTime
        binding.tvPlace.text = ticket.address
    }
}