package com.mewz.mymoviebookingapp.ui.viewholders.movie

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.databinding.ViewHolderMovieDataCardBinding
import com.mewz.mymoviebookingapp.ui.delgates.CinemaDetailViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.utils.DateCard

class MovieDateCardViewHolder(itemView: View, private var delegate: CinemaDetailViewHolderDelegate) :RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderMovieDataCardBinding
    private var mDateCard: DateCard? = null

    init {
        binding = ViewHolderMovieDataCardBinding.bind(itemView)
        mDateCard?.let {date ->
            date.latestTime?.let { delegate.onTapDateCard(it) }
            }
    }

    fun bindData(date: DateCard, position: Int) {
        mDateCard = date
        when(position) {
            0 -> {
                val today = "Today"
                binding.tvDay.text = today
            }
            1 -> {
                val tomorrow = "Tomorrow"
                binding.tvDay.text = tomorrow
            }
            else -> {
                binding.tvDay.text = date.dayOfWeek
            }
        }
        binding.tvMonth.text = date.month
        binding.tvDate.text = date.dayOfMonth
        mDateCard?.latestTime = date.latestTime
    }

    fun defaultColor(){
        binding.ivDateCard.setColorFilter(ContextCompat.getColor(itemView.context, R.color.white),android.graphics.PorterDuff.Mode.MULTIPLY)
    }

    fun selectColor(){
        binding.ivDateCard.setColorFilter(ContextCompat.getColor(itemView.context, R.color.green),android.graphics.PorterDuff.Mode.MULTIPLY)
    }
}