package com.mewz.mymoviebookingapp.ui.adapters.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.vos.movie.BannerVO
import com.mewz.mymoviebookingapp.ui.viewholders.movie.BannerViewHolder

class BannerAdapter:RecyclerView.Adapter<BannerViewHolder>() {

    private var mBannerList: List<BannerVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_banner,parent,false)
        return BannerViewHolder(view.rootView)
    }

    override fun getItemCount(): Int {
        return mBannerList.count()
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        if(mBannerList.isNotEmpty()){
            holder.bindData(mBannerList[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(bannerList: List<BannerVO>) {
        mBannerList = bannerList
        notifyDataSetChanged()
    }
}