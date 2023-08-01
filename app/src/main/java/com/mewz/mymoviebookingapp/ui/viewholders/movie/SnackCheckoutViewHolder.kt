package com.mewz.mymoviebookingapp.ui.viewholders.movie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackVO
import com.mewz.mymoviebookingapp.databinding.ViewHolderSnackCheckoutBinding
import com.mewz.mymoviebookingapp.ui.delgates.SnackCheckoutViewHolderDelegate

class SnackCheckoutViewHolder(itemView: View,private val delegate: SnackCheckoutViewHolderDelegate)
    :RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderSnackCheckoutBinding

    init {
        binding = ViewHolderSnackCheckoutBinding.bind(itemView)
    }

    fun bindData(snack: SnackVO, type: String) {
        binding.tvFoodName.text = snack.name

        val snackPrice = "${(snack.price?.times(snack.quantity))}Ks"
        binding.tvQty.text = snack.quantity.toString()
        binding.tvFoodPrice.text = snackPrice

        if(type == "Checkout") {
            binding.ivCancelSnack.setOnClickListener {
                delegate.onTapSnack(snack.id ?: 0)
            }
        } else {
            binding.ivCancelSnack.visibility = View.GONE
        }
    }
}