package com.mewz.mymoviebookingapp.ui.adapters.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaConfigVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaVO
import com.mewz.mymoviebookingapp.ui.delgates.CinemaDetailViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.viewholders.movie.MovieCinemaViewHolder

class MovieCinemaAdapter(private var delegate: CinemaDetailViewHolderDelegate):RecyclerView.Adapter<MovieCinemaViewHolder>() {

    private var mCinemaList : List<CinemaVO> = listOf()
    private var mTimeslotConfig: CinemaConfigVO? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCinemaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie_cinema,parent,false)
        return MovieCinemaViewHolder(view.rootView,delegate)
    }

    override fun getItemCount(): Int {
        return mCinemaList.count()
    }

    override fun onBindViewHolder(holder: MovieCinemaViewHolder, position: Int) {
        if (mCinemaList.isNotEmpty()){
            holder.bindData(mCinemaList[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cinemaList: List<CinemaVO>) {
        mCinemaList = cinemaList
        notifyDataSetChanged()
    }

}