package com.mewz.mymoviebookingapp.ui.adapters.movie

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaConfigVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaTimeslotVO
import com.mewz.mymoviebookingapp.ui.delgates.CinemaDetailViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.viewholders.movie.MovieTimeViewHolder

class MovieTimeAdapter(
    private val delegate: CinemaDetailViewHolderDelegate
):RecyclerView.Adapter<MovieTimeViewHolder>() {

    private var mTimeslotList: List<CinemaTimeslotVO> = listOf()
    private var mTimeslotConfig: CinemaConfigVO? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie_time,parent,false)
        return MovieTimeViewHolder(view.rootView,delegate)
    }

    override fun getItemCount(): Int {
        return mTimeslotList.count()
    }

    override fun onBindViewHolder(holder: MovieTimeViewHolder, position: Int) {
        if (mTimeslotList.isNotEmpty()){
            // TODO : create a logic to check whether which color is used
            holder.bindData(mTimeslotList[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(timeslotList: List<CinemaTimeslotVO>?) {
        if(timeslotList != null){
            mTimeslotList = timeslotList
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setConfigData(data: CinemaConfigVO?) {
        mTimeslotConfig = data
        notifyDataSetChanged()
    }

    // TODO : create a new method to accept color

}