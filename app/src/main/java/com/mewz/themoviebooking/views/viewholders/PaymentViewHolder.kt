package com.mewz.themoviebooking.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mewz.themoviebooking.data.vos.confirmation.PaymentVO
import com.mewz.themoviebooking.databinding.ViewholderPaymentBinding
import com.mewz.themoviebooking.delegates.PaymentViewHolderDelegate

class PaymentViewHolder(itemView: View, private var delegate: PaymentViewHolderDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderPaymentBinding

    private var mPayment: PaymentVO? = null

    init {
        binding = ViewholderPaymentBinding.bind(itemView)

        itemView.setOnClickListener {
            delegate.onTapPayment(mPayment?.id ?: 0)
        }
    }

    fun bindData(payment: PaymentVO) {

        mPayment = payment

        Glide.with(itemView.context)
            .load(payment.icon)
            .into(binding.ivPaymentType)

        binding.tvPaymentName.text = payment.title
    }
}