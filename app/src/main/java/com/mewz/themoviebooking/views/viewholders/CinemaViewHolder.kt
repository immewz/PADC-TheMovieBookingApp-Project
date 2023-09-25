package com.mewz.themoviebooking.views.viewholders

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.adapters.TimeAdapter
import com.mewz.themoviebooking.data.vos.cinema.CinemaVO
import com.mewz.themoviebooking.data.vos.cinema.TimeslotVO
import com.mewz.themoviebooking.databinding.ViewholderCinemaBinding
import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate

class CinemaViewHolder(itemView: View, private var delegate: CinemaViewHolderDelegate
 ) : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderCinemaBinding

    private lateinit var mAdapter: TimeAdapter

    private var mCinema: CinemaVO? = null
    private var isClickedCinema: Boolean = false

    init {
        binding = ViewholderCinemaBinding.bind(itemView)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = TimeAdapter(delegate)
        binding.rvTimeList.adapter = mAdapter
        binding.rvTimeList.layoutManager =
            GridLayoutManager(itemView.context, 3, GridLayoutManager.VERTICAL, false)
    }


    fun bindData(cinema: CinemaVO) {

        mCinema = cinema
        binding.tvCinemaName.text = cinema.cinema

        cinema.timeslots?.let { setUpRequestData(it) }

        setUpListeners()

    }

    private fun setUpListeners() {
        itemView.setOnClickListener {
            if (isClickedCinema){
                binding.rlCinema.visibility = View.GONE
                isClickedCinema = false
            }else{
                binding.rlCinema.visibility = View.VISIBLE
                delegate.getCinemaId(mCinema?.cinemaId)
                delegate.getCinemaName(mCinema?.cinema)
                isClickedCinema = true
            }
        }

        binding.btnSeeDetail.setOnClickListener {
            delegate.onTapSeeDetailCinema(mCinema?.cinemaId)
        }

    }

    private fun setUpRequestData(timeslots: List<TimeslotVO>) {
        mAdapter.setNewData(timeslots)
    }
}