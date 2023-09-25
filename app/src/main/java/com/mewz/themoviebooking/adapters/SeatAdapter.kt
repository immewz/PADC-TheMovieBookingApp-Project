package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.cinema.SeatVO
import com.mewz.themoviebooking.delegates.SeatViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.SeatViewHolder

class SeatAdapter(
    private val delegate: SeatViewHolderDelegate
): RecyclerView.Adapter<SeatViewHolder>() {

    private var mData: MutableList<MutableList<SeatVO>> = mutableListOf()
    private var numberOfColumn = 18

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_seat, parent, false)
        return SeatViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mData.count() * numberOfColumn
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData, holder.absoluteAdapterPosition)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(seatList: MutableList<MutableList<SeatVO>>) {
        mData = seatList
        notifyDataSetChanged()
    }
}