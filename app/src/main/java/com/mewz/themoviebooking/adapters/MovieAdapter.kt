package com.mewz.themoviebooking.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.vos.movie.MovieVO
import com.mewz.themoviebooking.delegates.MovieViewHolderDelegate
import com.mewz.themoviebooking.views.viewholders.MovieViewHolder

class MovieAdapter(
    private var isComingSoon: Boolean,
    private var delegate: MovieViewHolderDelegate
): RecyclerView.Adapter<MovieViewHolder>() {

    private var mData: List<MovieVO> = listOf()
    private var mToken: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_movie, parent, false)
        return MovieViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.findViewById<MaterialCardView>(R.id.mcvReleaseDate).visibility =
            if (isComingSoon) View.VISIBLE else View.GONE
        if (mData.isNotEmpty()){
            holder.bindData(mData[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewDataForNowShowing(movieList: List<MovieVO>) {
        mData = movieList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewDataForComingSoon(movieList: List<MovieVO>) {
        mData = movieList
        notifyDataSetChanged()
    }
}