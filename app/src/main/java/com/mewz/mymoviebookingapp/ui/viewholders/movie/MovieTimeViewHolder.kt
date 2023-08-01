package com.mewz.mymoviebookingapp.ui.viewholders.movie

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaConfigVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaTimeslotColorVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaTimeslotVO
import com.mewz.mymoviebookingapp.databinding.ViewHolderMovieTimeBinding
import com.mewz.mymoviebookingapp.ui.delgates.CinemaDetailViewHolderDelegate
import java.util.ArrayList

class MovieTimeViewHolder(itemView: View,private var delegate: CinemaDetailViewHolderDelegate) :RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderMovieTimeBinding
    private var mCinemaTimeslotVO: CinemaTimeslotVO? = null

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    init {
        binding = ViewHolderMovieTimeBinding.bind(itemView)
        itemView.setOnLongClickListener {
            mCinemaTimeslotVO?.let {timeslot ->
                timeslot.cinemaDayTimeslotId?.let { id -> timeslot.startTime?.let { time ->
                    delegate.onTapTimeslot(id,
                        time
                    )}
                }
            }
            true
        }
    }

    private fun changeToListTimeslotColorVO(): ArrayList<CinemaTimeslotColorVO>{
        val oldConfigList = mMovieBookingModel.getCinemaConfig("cinema_timeslot_status")?.value as ArrayList<*>
        val newConfigList = arrayListOf<CinemaTimeslotColorVO>()
        for (oldConfig in oldConfigList){
            val gson = Gson()
            val linkedTreeMap: LinkedTreeMap<*, *> = oldConfig as LinkedTreeMap<*, *>
            val timeslotColorVO: CinemaTimeslotColorVO = gson.fromJson(gson.toJsonTree(linkedTreeMap), CinemaTimeslotColorVO::class.java)
            newConfigList.add(timeslotColorVO)
        }
        return newConfigList
    }

    private fun getTimeslotColor(timeslot: CinemaTimeslotVO): String{
        var color = ""
        val configList = changeToListTimeslotColorVO()
        for (config in configList){
            if (config.id == timeslot.status){
                color = config.color ?: ""
            }
        }
        return color
    }

//
//    private fun setUpTimeslotConfig(cinemaConfig: CinemaTimeslotVO, timeslotConfig: CinemaConfigVO): String {
//        var color = ""
//        var configList = changeToListTimeslotColorVO(timeslotConfig.value as List<*>)
//        for(config in configList) {
//            if (config.id == cinemaConfig.status ){
//                color = config.color ?: ""
////                Log.d("Config", "requestColor.${color}")
//            }
//
//        }
//        return color
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


    fun bindData(timeslotList: CinemaTimeslotVO) {
        mCinemaTimeslotVO = timeslotList
        binding.tvCinemaTime.text = timeslotList.startTime

        val color = getTimeslotColor(timeslotList)
        Log.d("Config", "requestColor.${color}")

        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(itemView.resources.getColor(R.color.bg))
        shape.cornerRadius = itemView.resources.getDimension(R.dimen.margin_small)
        shape.setStroke(1, Color.parseColor(color))
        binding.llTimeslot.background = shape
        Log.d("Config", "requestColor.${color}")

    }
}