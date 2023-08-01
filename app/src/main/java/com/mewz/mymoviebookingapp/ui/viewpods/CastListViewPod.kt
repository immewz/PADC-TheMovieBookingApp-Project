package com.mewz.mymoviebookingapp.ui.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.mewz.mymoviebookingapp.data.vos.movie.CastVO
import com.mewz.mymoviebookingapp.databinding.ViewPodCastListBinding
import com.mewz.mymoviebookingapp.ui.adapters.movie.CastAdapter

class CastListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding: ViewPodCastListBinding
    private lateinit var mCastAdapter: CastAdapter

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewPodCastListBinding.bind(this)
    }

    fun setUpCastListPod(){
        setCastRecyclerView()
    }

    fun setData(castList: List<CastVO>) {
        mCastAdapter.setNewData(castList)
    }

    private fun setCastRecyclerView(){
        mCastAdapter = CastAdapter()
        binding.rvCastList.adapter = mCastAdapter
        binding.rvCastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }


}