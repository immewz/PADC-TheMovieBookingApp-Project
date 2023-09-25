package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.confirmation.PaymentVO
import com.mewz.themoviebooking.delegates.PaymentViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.PaymentViewHolder

class PaymentAdapter(
    private var delegate: PaymentViewHolderDelegate
): RecyclerView.Adapter<PaymentViewHolder>() {

    private var mData: List<PaymentVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_payment, parent, false)
        return PaymentViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(payment: List<PaymentVO>) {
        mData = payment
        notifyDataSetChanged()
    }
}