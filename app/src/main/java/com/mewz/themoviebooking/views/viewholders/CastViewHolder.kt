package com.mewz.themoviebooking.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.themoviebooking.data.vos.movie.CastVO
import com.mewz.themoviebooking.databinding.ViewholderCastBinding
import com.mewz.themoviebooking.network.IMAGE_BASE_URL

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    private var binding: ViewholderCastBinding

    init {
        binding = ViewholderCastBinding.bind(itemView)
    }

    fun bindData(cast: CastVO) {

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${cast.profilePath}")
            .into(binding.ivCastImage)

        binding.tvCastName.text = cast.name
    }
}