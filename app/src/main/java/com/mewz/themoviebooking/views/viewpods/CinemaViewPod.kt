package com.mewz.themoviebooking.views.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mewz.themoviebooking.adapters.CinemaAdapter
import com.mewz.themoviebooking.data.vos.cinema.CinemaVO
import com.mewz.themoviebooking.databinding.ViewpodCinemaBinding
import com.mewz.themoviebooking.delegates.CinemaViewHolderDelegate

class CinemaViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding: ViewpodCinemaBinding

    private lateinit var mAdapter: CinemaAdapter

    private lateinit var mDelegate: CinemaViewHolderDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewpodCinemaBinding.bind(this)
    }

    fun setUpCinemaViewPod(delegate: CinemaViewHolderDelegate){
        this.mDelegate = delegate
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = CinemaAdapter(mDelegate)
        binding.rvCinemaList.adapter = mAdapter
        binding.rvCinemaList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun setData(cinemaList: List<CinemaVO>) {
        mAdapter.setNewData(cinemaList)
    }
}