package com.mewz.themoviebooking.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.cinema.SeatVO
import com.mewz.themoviebooking.databinding.ViewholderSeatBinding
import com.mewz.themoviebooking.delegates.SeatViewHolderDelegate

class SeatViewHolder(itemView: View, private val delegate: SeatViewHolderDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderSeatBinding

    private var mSeatVO: SeatVO? = null
    private var numberOfColumn = 18

    init {
        binding = ViewholderSeatBinding.bind(itemView)
        setUpListener()
    }

    private fun setUpListener() {
        binding.ivSeat.setOnClickListener {
            if (mSeatVO?.isSelected == false){
                delegate.onTapSeat(mSeatVO?.seatName ?: "", mSeatVO?.isSelected)
            }else{
                delegate.onTapSeat(mSeatVO?.seatName ?: "", mSeatVO?.isSelected)
            }
        }
    }

    fun bindData(seatList: MutableList<MutableList<SeatVO>>, position: Int) {

        val row = position / numberOfColumn
        val column = position % numberOfColumn
        val seat = seatList[row][column]

        mSeatVO = seat

        if (mSeatVO?.isSelected == true){
            binding.ivSeat.setColorFilter(itemView.resources.getColor(R.color.colorAccent))
        }else{
            binding.ivSeat.setColorFilter(itemView.resources.getColor(R.color.white))
        }

        when(seat.type){
            "text" -> {
                binding.tvSeatRow.visibility = View.VISIBLE
                binding.ivSeat.visibility = View.INVISIBLE
                binding.tvSeatRow.text = seat.symbol
            }
            "space","path" -> {
                binding.tvSeatRow.visibility = View.INVISIBLE
                binding.ivSeat.visibility = View.INVISIBLE
            }
            "taken" -> {
                binding.tvSeatRow.visibility = View.INVISIBLE
                binding.ivSeat.visibility = View.VISIBLE
                binding.ivSeat.setColorFilter(itemView.resources.getColor(R.color.colorChairTaken))
            }
            "available" -> {
                binding.tvSeatRow.visibility = View.INVISIBLE
                binding.ivSeat.visibility = View.VISIBLE
            }
        }
    }
}