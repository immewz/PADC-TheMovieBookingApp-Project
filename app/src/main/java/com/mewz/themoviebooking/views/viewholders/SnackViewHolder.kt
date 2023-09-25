package com.mewz.themoviebooking.views.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.databinding.ViewholderSnackBinding
import com.mewz.themoviebooking.delegates.SnackViewHolderDelegate

class SnackViewHolder(itemView: View, private var delegate: SnackViewHolderDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderSnackBinding

    private var mSnack: SnackVO? = null

    init {
        binding = ViewholderSnackBinding.bind(itemView)

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnAddSnack.setOnClickListener {
            binding.btnAddSnack.visibility = View.GONE
            binding.llSnackCount.visibility = View.VISIBLE
        }

        binding.btnPlus.setOnClickListener {
            delegate.onTapPlusSnack(mSnack?.id ?: 0)
        }

        binding.btnMinus.setOnClickListener {
           delegate.onTapMinusSnack(mSnack?.id ?: 0)
        }
    }

    @SuppressLint("SetTextI18n")
    fun bindData(snack: SnackVO) {

        mSnack = snack

        Glide.with(itemView.context)
            .load(snack.image)
            .into(binding.ivSnack)

        binding.tvSnackName.text = snack.name
        binding.tvSnackPrice.text = "${snack.price} Ks"

        setUpAddButtonOrCountButton()
    }

    private fun setUpAddButtonOrCountButton() {
        if (mSnack?.quantity!! <= 0){
            binding.btnAddSnack.visibility = View.VISIBLE
            binding.llSnackCount.visibility = View.GONE
            binding.tvSnackCount.text = "0"
        }else{
            binding.btnAddSnack.visibility = View.GONE
            binding.llSnackCount.visibility = View.VISIBLE
            binding.tvSnackCount.text = mSnack?.quantity.toString()
        }
    }
}