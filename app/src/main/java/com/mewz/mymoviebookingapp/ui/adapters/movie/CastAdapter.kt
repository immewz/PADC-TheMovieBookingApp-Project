package com.mewz.mymoviebookingapp.ui.adapters.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.vos.movie.CastVO
import com.mewz.mymoviebookingapp.ui.viewholders.movie.CastViewHolder

class CastAdapter:RecyclerView.Adapter<CastViewHolder>() {

    private var mCastList : List<CastVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cast,parent,false)
        return CastViewHolder(view.rootView)
    }

    override fun getItemCount(): Int {
        return mCastList.count()
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        if(mCastList.isNotEmpty()){
            holder.bindData(mCastList[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(castList: List<CastVO>){
        mCastList = castList
        notifyDataSetChanged()
    }
}