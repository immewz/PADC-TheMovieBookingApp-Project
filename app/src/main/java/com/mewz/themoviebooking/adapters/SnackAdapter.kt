package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.delegates.SnackViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.SnackViewHolder

class SnackAdapter(
    private var delegate: SnackViewHolderDelegate
): RecyclerView.Adapter<SnackViewHolder>() {

    private var mData: List<SnackVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_snack, parent, false)
        return SnackViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {
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