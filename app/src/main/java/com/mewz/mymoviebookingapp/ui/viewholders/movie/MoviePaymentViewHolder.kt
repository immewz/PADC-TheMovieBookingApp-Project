package com.mewz.mymoviebookingapp.ui.viewholders.movie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.mymoviebookingapp.data.vos.movie.PaymentVO
import com.mewz.mymoviebookingapp.databinding.ViewHolderMoviePaymentBinding
import com.mewz.mymoviebookingapp.ui.delgates.PaymentViewHolderDelegate

class MoviePaymentViewHolder(itemView: View, delegate: PaymentViewHolderDelegate) :RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderMoviePaymentBinding
    private var mPayment: PaymentVO? = null

    init {
        binding = ViewHolderMoviePaymentBinding.bind(itemView)
        itemView.setOnClickListener {
            delegate.onTapPayment(mPayment?.id ?: 0)
        }
    }


    fun bindData(payment: PaymentVO) {
        mPayment = payment
        Glide.with(itemView.context)
            .load(payment.icon)
            .into(binding.ivPaylogo)

        binding.tvPayment.text = payment.title
    }
}