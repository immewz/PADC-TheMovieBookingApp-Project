package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.delegates.SnackTicketViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.SnackTicketViewHolder

class SnackTicketAdapter(
    private val delegate: SnackTicketViewHolderDelegate
): RecyclerView.Adapter<SnackTicketViewHolder>() {

    private var mData: List<SnackVO> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SnackTicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_snack_ticket, parent, false)
        return SnackTicketViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: SnackTicketViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(snackList: List<SnackVO>) {
        mData = snackList
        notifyDataSetChanged()
    }
}