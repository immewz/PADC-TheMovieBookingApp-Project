package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.views.viewholders.SnackBottomSheetViewHolder

class SnackBottomSheetAdapter: RecyclerView.Adapter<SnackBottomSheetViewHolder>() {

    private var mData: MutableList<SnackVO> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackBottomSheetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_snack_bottom_sheet, parent ,false)
        return SnackBottomSheetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.count()
    }

    override fun onBindViewHolder(holder: SnackBottomSheetViewHolder, position: Int) {
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(snackList: MutableList<SnackVO>) {
        mData = snackList
        notifyDataSetChanged()
    }

}

