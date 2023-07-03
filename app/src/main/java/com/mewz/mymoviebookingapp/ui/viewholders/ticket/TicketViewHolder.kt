package com.mewz.mymoviebookingapp.ui.viewholders.ticket

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaVO
import com.mewz.mymoviebookingapp.data.vos.ticket.TicketInformation
import com.mewz.mymoviebookingapp.databinding.ViewHolderTicketBinding
import com.mewz.mymoviebookingapp.network.responses.movie.cinema.CinemaTimeslotResponse
import com.mewz.mymoviebookingapp.network.utiles.IMAGE_BASE_URL
import com.mewz.mymoviebookingapp.ui.delgates.TicketViewHolderDelegate

class TicketViewHolder(itemView: View, private var delegate: TicketViewHolderDelegate) :RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderTicketBinding
    private var mTicket: TicketInformation? = null
    private var mCinemaTimeslotResponse: CinemaTimeslotResponse? = null

    init {
        binding = ViewHolderTicketBinding.bind(itemView)
        itemView.setOnClickListener {
            mTicket?.id?.let { it -> delegate.onTapTicket(it) }
        }
    }

    fun bindData(ticket: TicketInformation?) {
        mTicket = ticket
        binding.tvMovieTitle.text = ticket?.movieName ?: ""
        binding.tvCinema.text = ticket?.ticketCheckout?.bookingNo ?: ""
        binding.tvTicketCount.text = ticket?.ticketCheckout?.totalSeat.toString()
        binding.tvReceiptSeat.text = ticket?.ticketCheckout?.seat ?: ""
        binding.tvCinemaDate.text = ticket?.ticketCheckout?.bookingDate ?: ""
        binding.tvCinemaTime.text = ticket?.ticketCheckout?.timeslot?.startTime ?: ""
        binding.tvCinemaLocation.text = ticket?.address ?: ""

        val cinemaId = ticket?.ticketCheckout?.cinemaId
        Log.d("TicketVHID","dataTicketId.$cinemaId")
        mCinemaTimeslotResponse?.data?.let{
            for (list in it){
                if (list.cinemaId == cinemaId){
                    binding.tvCinema.text = list.cinema
                    Log.d("TicketVHID","dataTicketName.${list.cinema}")
                }
            }
        }

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${ticket?.moviePoster}")
            .into(binding.ivMovie)
    }
}