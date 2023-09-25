package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.cinema.TimeslotVO
import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.TimeViewHolder

class TimeAdapter(
    private var delegate: CinemaViewHolderDelegate
): RecyclerView.Adapter<TimeViewHolder>() {

    private var mData: List<TimeslotVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_time, parent, false)
        return TimeViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(timeslots: List<TimeslotVO>) {
        mData = timeslots
        notifyDataSetChanged()
    }
}