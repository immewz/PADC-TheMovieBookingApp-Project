package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.cinema.CinemaVO
import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.CinemaViewHolder

class CinemaAdapter(
    private var delegate: CinemaViewHolderDelegate
): RecyclerView.Adapter<CinemaViewHolder>() {

    private var mData: List<CinemaVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cinema, parent, false)
        return CinemaViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cinemaList: List<CinemaVO>) {
        mData = cinemaList
        notifyDataSetChanged()
    }
}