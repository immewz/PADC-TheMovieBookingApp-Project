package com.mewz.mymoviebookingapp.ui.viewholders.movie

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaConfigVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaVO
import com.mewz.mymoviebookingapp.databinding.ViewHolderMovieCinemaBinding
import com.mewz.mymoviebookingapp.ui.adapters.movie.MovieTimeAdapter
import com.mewz.mymoviebookingapp.ui.delgates.CinemaDetailViewHolderDelegate

class MovieCinemaViewHolder(itemView: View,private var delegate: CinemaDetailViewHolderDelegate) :RecyclerView.ViewHolder(itemView){

    private var binding: ViewHolderMovieCinemaBinding
    private var mMovieTimeAdapter: MovieTimeAdapter? = null
    private var mCinema: CinemaVO? = null

    private var isVisibleReyclerView: Boolean = false


    init {
        binding = ViewHolderMovieCinemaBinding.bind(itemView)
        itemView.setOnClickListener {
            mCinema?.let { cinema ->
                delegate.getCinemaName(cinema.cinema)
                delegate.getCinemaId(cinema.cinemaId)
            }
            if (isVisibleReyclerView){
                binding.rvTimeList.visibility = View.GONE
                binding.llLongPress.visibility = View.GONE
                isVisibleReyclerView = false
            }else{
                binding.rvTimeList.visibility = View.VISIBLE
                binding.llLongPress.visibility = View.VISIBLE
                setUpTimeRecyclerView()
                requestTimeSlotData()
                isVisibleReyclerView = true
            }

        }

        setUpListener()
    }

    private fun setUpListener() {
        binding.btnSeeDetail.setOnClickListener {
            mCinema?.let {cinema ->
                delegate.onTapSeeDetail(cinema.cinemaId ?: 0)
            }
        }
    }

    private fun setUpTimeRecyclerView() {
        mMovieTimeAdapter = MovieTimeAdapter(delegate)
        binding.rvTimeList.adapter = mMovieTimeAdapter
        binding.rvTimeList.layoutManager = GridLayoutManager(itemView.context,3,
            GridLayoutManager.VERTICAL,false)
    }

    fun bindData(cinema: CinemaVO) {
        mCinema = cinema
        binding.tvCinemaLocation.text = cinema.cinema

        if (mCinema?.isClicked == false) {
            binding.rvTimeList.visibility = View.GONE
            binding.llLongPress.visibility = View.GONE
            mMovieTimeAdapter = null
        } else {
            binding.rvTimeList.visibility = View.VISIBLE
            binding.llLongPress.visibility = View.VISIBLE
            setUpTimeRecyclerView()
            requestTimeSlotData()
        }

        // TODO : parse color to new method in adapter
//        if (cinemaConfig != null) {
//            setUpTimeslotConfig(cinemaConfig)
//            mMovieTimeAdapter?.setConfigData(cinemaConfig)
//        }


    }

//    private fun setUpTimeslotConfig(cinemaConfig: CinemaConfigVO) {
//        var color = ""
//        var configList = changeToListTimeslotColorVO(cinemaConfig.value as List<*>)
//        for(config in configList) {
//            color = config.color ?: ""
//        }
//        Log.d("Config", "requestCColor.${color}")
//    }
//
//    private fun changeToListTimeslotColorVO(oldConfigList: List<*>): ArrayList<CinemaConfigValueVO> {
//        val newConfigList = arrayListOf<CinemaConfigValueVO>()
//        for(oldConfig in oldConfigList) {
//            val gson = Gson()
//            val linkedTreeMap: LinkedTreeMap<*, *> = oldConfig as LinkedTreeMap<*, *>
//            val timeslotColorVO: CinemaConfigValueVO = gson.fromJson(gson.toJsonTree(linkedTreeMap), CinemaConfigValueVO::class.java)
//            newConfigList.add(timeslotColorVO)
//        }
//        return newConfigList
//    }

    private fun requestTimeSlotData() {
        mMovieTimeAdapter?.setNewData(mCinema?.timeslots)
    }


}