package com.mewz.themoviebooking.views.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.mewz.themoviebooking.adapters.MovieAdapter
import com.mewz.themoviebooking.data.vos.movie.MovieVO
import com.mewz.themoviebooking.databinding.ViewpodMovieBinding
import com.mewz.themoviebooking.delegates.MovieViewHolderDelegate

class MovieViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding: ViewpodMovieBinding

    private lateinit var mNowShowingAdapter: MovieAdapter
    private lateinit var mComingSoonAdapter: MovieAdapter

    private lateinit var mDelegate: MovieViewHolderDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewpodMovieBinding.bind(this)
    }

    fun setUpNowShowingViewPod(delegate: MovieViewHolderDelegate){
        this.mDelegate = delegate
        setUpNowShowingRecyclerView()
    }

    fun setUpComingSoonViewPod(delegate: MovieViewHolderDelegate){
        this.mDelegate = delegate
        setUpComingSoonRecyclerView()
    }

    private fun setUpNowShowingRecyclerView() {
        mNowShowingAdapter = MovieAdapter(false, mDelegate)
        binding.rvMovieList.adapter = mNowShowingAdapter
        binding.rvMovieList.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

    private fun setUpComingSoonRecyclerView() {
        mComingSoonAdapter = MovieAdapter(true, mDelegate)
        binding.rvMovieList.adapter = mComingSoonAdapter
        binding.rvMovieList.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

    fun setDataForNowShowing(movieList: List<MovieVO>) {
        mNowShowingAdapter.setNewDataForNowShowing(movieList)
    }

    fun setDataForComingSoon(movieList: List<MovieVO>) {
        mComingSoonAdapter.setNewDataForComingSoon(movieList)
    }
}