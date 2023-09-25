package com.mewz.themoviebooking.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.databinding.ViewholderDatacardBinding
import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate
import com.mewz.themoviebooking.utils.data.TimeslotData

class DateCardViewHolder(itemView: View, private var delegate: CinemaViewHolderDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderDatacardBinding

    private var mTimeslot: TimeslotData? = null

    init {
        binding = ViewholderDatacardBinding.bind(itemView)
    }

    fun bindData(data: TimeslotData, position: Int){
        mTimeslot = data
        when (position) {
            0 -> {
                val today = "Today"
                binding.tvDayOfWeek.text = today
            }
            1 -> {
                val tomorrow = "Tomorrow"
                binding.tvDayOfWeek.text = tomorrow
            }
            else -> {
                binding.tvDayOfWeek.text = data.dayOfWeek
            }
        }
        binding.tvMonth.text = data.month
        binding.tvDayOfMonth.text = data.dayOfMonth
        mTimeslot?.latestTime = data.latestTime
    }
}