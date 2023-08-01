package com.mewz.mymoviebookingapp.ui.adapters.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.vos.movie.PaymentVO
import com.mewz.mymoviebookingapp.ui.delgates.PaymentViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.viewholders.movie.MoviePaymentViewHolder

class MoviePaymentAdapter(
    private val delegate: PaymentViewHolderDelegate
):RecyclerView.Adapter<MoviePaymentViewHolder>() {

    private var mPaymentList: List<PaymentVO>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePaymentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie_payment,parent,false)
        return MoviePaymentViewHolder(view.rootView,delegate)
    }

    override fun getItemCount(): Int {
        return mPaymentList?.count()!!
    }

    override fun onBindViewHolder(holder: MoviePaymentViewHolder, position: Int) {
        if(mPaymentList?.isNotEmpty()!!){
            holder.bindData(mPaymentList!![position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(payment: List<PaymentVO>) {
        mPaymentList = payment
        notifyDataSetChanged()
    }
}