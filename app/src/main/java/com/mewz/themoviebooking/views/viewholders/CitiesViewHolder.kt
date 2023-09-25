package com.mewz.themoviebooking.views.viewholders

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.data.vos.login.CitiesVO
import com.mewz.themoviebooking.databinding.ViewholderCitiesBinding
import com.mewz.themoviebooking.delegates.CitiesViewHolderDelegate

class CitiesViewHolder(itemView: View, private val delegate: CitiesViewHolderDelegate)
    : RecyclerView.ViewHolder(itemView) {

    private var binding : ViewholderCitiesBinding

    init {
        binding = ViewholderCitiesBinding.bind(itemView)

    }

    fun bindData(city: CitiesVO) {
        binding.tvLocation.text = city.name

        itemView.setOnClickListener {
            delegate.onTapCities(city.name.toString())
        }
    }
}