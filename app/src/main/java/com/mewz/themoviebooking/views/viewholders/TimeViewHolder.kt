package com.mewz.themoviebooking.views.viewholders

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.data.vos.cinema.TimeslotColorVO
import com.mewz.themoviebooking.data.vos.cinema.TimeslotVO
import com.mewz.themoviebooking.databinding.ViewholderTimeBinding
import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate

class TimeViewHolder(itemView: View, private var delegate: CinemaViewHolderDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewholderTimeBinding

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var mTimeslot: TimeslotVO? = null

    init {
        binding = ViewholderTimeBinding.bind(itemView)

        setUpLongClickListener()
    }

    private fun setUpLongClickListener() {
        itemView.setOnLongClickListener {
            delegate.onTapTimeslotCinema(mTimeslot?.cinemaDayTimeslotId ?: 0, mTimeslot?.startTime)
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun bindData(timeslot: TimeslotVO) {

        mTimeslot = timeslot
        binding.tvCinemaTime.text = timeslot.startTime

        val color = getTimeslotColor(timeslot)

        val shape = GradientDrawable()
        shape.setColor(itemView.resources.getColor(R.color.background,null))
        shape.cornerRadius = itemView.resources.getDimension(R.dimen.margin_small)
        shape.setStroke(1, Color.parseColor(color))
        binding.itemBox.background = shape
    }

    private fun getTimeslotColor(timeslot: TimeslotVO): String {
        var color = ""
        val configList = changeToListTimeslotColorVO()
        for (config in configList) {
            if (config.id == timeslot.status) {
                color = config.color ?: ""
            }
        }
        return color
    }

    private fun changeToListTimeslotColorVO(): ArrayList<TimeslotColorVO> {
        val oldConfigList = mTheMovieBookingModel.getConfig("cinema_timeslot_status")?.value as ArrayList<*>
        val newConfigList = arrayListOf<TimeslotColorVO>()
        for (oldConfig in oldConfigList) {
            val linkedTreeMap = oldConfig as LinkedTreeMap<*, *>
            val timeslotColorVO = Gson().fromJson(Gson().toJsonTree(linkedTreeMap), TimeslotColorVO::class.java)
            newConfigList.add(timeslotColorVO)
        }
        return newConfigList
    }

}