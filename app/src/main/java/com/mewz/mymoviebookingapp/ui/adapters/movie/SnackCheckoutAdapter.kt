package com.mewz.mymoviebookingapp.ui.adapters.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackVO
import com.mewz.mymoviebookingapp.ui.delgates.SnackCheckoutViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.viewholders.movie.SnackCheckoutViewHolder

class SnackCheckoutAdapter(
    private var delegate: SnackCheckoutViewHolderDelegate
):RecyclerView.Adapter<SnackCheckoutViewHolder>() {

    private var mSnackList: List<SnackVO> = listOf()
    private lateinit var mType: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackCheckoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_snack_checkout,parent,false)
        return SnackCheckoutViewHolder(view.rootView, delegate)
    }

    override fun getItemCount(): Int {
        return mSnackList.size
    }

    override fun onBindViewHolder(holder: SnackCheckoutViewHolder, position: Int) {
        if (mSnackList.isNotEmpty()){
            holder.bindData(mSnackList[position], mType)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun bindNewData(snackList: List<SnackVO>, type: String) {
        mSnackList = snackList
        mType = type
        notifyDataSetChanged()
    }
}