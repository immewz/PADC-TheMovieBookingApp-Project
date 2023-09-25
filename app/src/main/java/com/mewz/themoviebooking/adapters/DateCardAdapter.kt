package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate
import com.mewz.themoviebooking.utils.data.TimeslotData
import com.mewz.themoviebooking.views.viewholders.DateCardViewHolder

class DateCardAdapter(
    private var dateListTimeslot: MutableList<String>,
    private var delegate: CinemaViewHolderDelegate
): RecyclerView.Adapter<DateCardViewHolder>() {

    private lateinit var mDateList: MutableList<TimeslotData>
    private var isSelectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_datacard, parent, false)
        return DateCardViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mDateList.size
    }

    override fun onBindViewHolder(holder: DateCardViewHolder, position: Int) {
        holder.bindData(mDateList[position], position)

        if (isSelectedPosition == position){
            holder.itemView.findViewById<RelativeLayout>(R.id.rlDateCard).setBackgroundResource(R.drawable.date_card_green)
            delegate.onTapDateCardCinema(dateListTimeslot[0])
        }else{
            holder.itemView.findViewById<RelativeLayout>(R.id.rlDateCard).setBackgroundResource(R.drawable.date_card)
        }

        holder.itemView.findViewById<RelativeLayout>(R.id.rlDateCard).setOnClickListener {
            val previousPosition = isSelectedPosition
            isSelectedPosition = holder.absoluteAdapterPosition
            notifyItemChanged(previousPosition)

            delegate.onTapDateCardCinema(dateListTimeslot[isSelectedPosition])
            holder.itemView.findViewById<RelativeLayout>(R.id.rlDateCard)
                .setBackgroundResource(R.drawable.date_card_green)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindData(dateList: MutableList<TimeslotData>) {
        mDateList = dateList
        notifyDataSetChanged()
    }
}