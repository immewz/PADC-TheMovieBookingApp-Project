package com.mewz.themoviebooking.views.viewholders

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.databinding.ViewholderSnackBottomSheetBinding

class SnackBottomSheetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderSnackBottomSheetBinding

    private var mSnack: SnackVO? = null

    init {
        binding = ViewholderSnackBottomSheetBinding.bind(itemView)
    }

    @SuppressLint("SetTextI18n")
    fun bindData(snack: SnackVO) {

        mSnack = snack

        binding.tvSnackName.text = snack.name
        binding.tvSnackCount.text = snack.quantity.toString()
        binding.tvSnackPrice.text = "${snack.price}Ks"
    }
}