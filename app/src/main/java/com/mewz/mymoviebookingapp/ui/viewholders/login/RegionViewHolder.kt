package com.mewz.mymoviebookingapp.ui.viewholders.login

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mewz.mymoviebookingapp.data.vos.login.CitiesVO
import com.mewz.mymoviebookingapp.databinding.ViewHolderRegionBinding
import com.mewz.mymoviebookingapp.ui.delgates.RegionViewHolderDelegate

class RegionViewHolder(itemView: View, delegate: RegionViewHolderDelegate) :RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderRegionBinding
    private var mCitiesVO: CitiesVO? = null

    init {
        binding = ViewHolderRegionBinding.bind(itemView)
        itemView.setOnClickListener {
            mCitiesVO?.let { city ->
                city.name?.let { it -> delegate.onTapRegion(it) }
            }
        }
    }

    fun bindData(cities: CitiesVO) {
        mCitiesVO = cities
        Log.d("RegionVh","data.${cities}")
        binding.tvRegion.text = cities.name
    }
}