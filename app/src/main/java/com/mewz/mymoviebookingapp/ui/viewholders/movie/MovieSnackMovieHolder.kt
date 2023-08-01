package com.mewz.mymoviebookingapp.ui.viewholders.movie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackVO
import com.mewz.mymoviebookingapp.databinding.ViewHolderMovieSnackBinding
import com.mewz.mymoviebookingapp.ui.delgates.SnackViewHolderDelegate

class MovieSnackMovieHolder(itemView: View,delegate: SnackViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderMovieSnackBinding
    private var mSnackVO: SnackVO? = null

    init {
        binding = ViewHolderMovieSnackBinding.bind(itemView)
        binding.btnFoodCount.setOnClickListener {
            binding.btnFoodCount.visibility = View.GONE
            binding.llFoodCount.visibility = View.VISIBLE
        }

        binding.btnAdd.setOnClickListener {
            delegate.onTapFoodCountPlus(mSnackVO?.id ?: 0)
        }

        binding.btnSub.setOnClickListener {
            delegate.onTapFoodCountMinus(mSnackVO?.id ?: 0)
        }
    }

    fun bindSnackData(snack: SnackVO) {
        mSnackVO = snack
        Glide.with(itemView.context)
            .load(snack.image)
            .into(binding.ivFood)

        binding.tvFoodName.text = snack.name
        binding.tvFoodPrice.text = snack.price.toString()

        setUpSnackOrderCount()
    }

    private fun setUpSnackOrderCount() { // btn change Add or plusOrMinus visibility
        if (mSnackVO?.quantity!! <= 0){
            binding.btnFoodCount.visibility = View.VISIBLE
            binding.llFoodCount.visibility = View.GONE
        }else{
            binding.btnFoodCount.visibility = View.GONE
            binding.llFoodCount.visibility = View.VISIBLE
            binding.tvOrderCount.text = mSnackVO?.quantity.toString()
        }
    }
}