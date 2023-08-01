package com.mewz.mymoviebookingapp.ui.viewholders.movie

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.mymoviebookingapp.data.vos.movie.BannerVO
import com.mewz.mymoviebookingapp.databinding.ViewHolderBannerBinding

class BannerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderBannerBinding

    init {
        binding = ViewHolderBannerBinding.bind(itemView)
    }

    fun bindData(banner: BannerVO) {
        Log.d("BannerViewHolder","bannerImage: ${banner.url}")
        Glide.with(itemView.context)
            .load("${banner.url}")
            .into(binding.ivBanner)
    }

}