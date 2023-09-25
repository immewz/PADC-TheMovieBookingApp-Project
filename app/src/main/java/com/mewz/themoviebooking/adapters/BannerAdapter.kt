package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.movie.BannerVO
import com.mewz.themoviebooking.views.viewholders.BannerViewHolder

class BannerAdapter: RecyclerView.Adapter<BannerViewHolder>() {

    private var mData: List<BannerVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(bannerList: List<BannerVO>) {
        mData = bannerList
        notifyDataSetChanged()
    }
}