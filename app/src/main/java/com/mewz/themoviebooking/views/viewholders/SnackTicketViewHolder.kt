package com.mewz.themoviebooking.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.databinding.ViewholderSnackTicketBinding
import com.mewz.themoviebooking.delegates.SnackTicketViewHolderDelegate

class SnackTicketViewHolder(itemView: View, private val delegate: SnackTicketViewHolderDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderSnackTicketBinding

    private var mSnack: SnackVO? = null

    init {
        binding = ViewholderSnackTicketBinding.bind(itemView)

        binding.btnDelete.setOnClickListener {
            delegate.onTapSnack(mSnack?.id ?: 0)
        }
    }

    fun bindData(snack: SnackVO) {

        mSnack = snack

        binding.tvSnackName.text = snack.name
        binding.tvSnackCount.text = "(Qty. ${snack.quantity})"

        val snackPrice = "${snack.price?.times(snack.quantity ?: 0) ?: 0}Ks"
        binding.tvSnackPrice.text = snackPrice

    }
}