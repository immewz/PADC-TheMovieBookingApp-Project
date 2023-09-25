package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.login.CitiesVO
import com.mewz.themoviebooking.delegates.CitiesViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.CitiesViewHolder

class CitiesAdapter(
    private val delegate: CitiesViewHolderDelegate
): RecyclerView.Adapter<CitiesViewHolder>() {

    private var mData: List<CitiesVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cities, parent, false)
        return CitiesViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(citiesList: List<CitiesVO>) {
        mData = citiesList
        notifyDataSetChanged()
    }
}