package com.mewz.themoviebooking.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.themoviebooking.data.vos.movie.BannerVO
import com.mewz.themoviebooking.databinding.ViewholderBannerBinding

class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderBannerBinding

    init {
        binding = ViewholderBannerBinding.bind(itemView)
    }

    fun bindData(banner: BannerVO) {

        Glide.with(itemView.context)
            .load("${banner.url}")
            .into(binding.ivBannerBoard)

    }
}