package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.delegates.TicketViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.TicketViewHolder

class TicketAdapter(
    private var delegate: TicketViewHolderDelegate,
    private var type: String
): RecyclerView.Adapter<TicketViewHolder>() {

    private var mData: List<TicketInformation>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_ticket, parent, false)
        return TicketViewHolder(view, delegate, type)
    }

    override fun getItemCount(): Int {
        return mData?.size!!
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        mData?.get(position)?.let {
            holder.bindData(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(ticketInformation: List<TicketInformation>) {
        mData = ticketInformation
        notifyDataSetChanged()
    }
}