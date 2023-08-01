package com.mewz.mymoviebookingapp.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayout
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.databinding.FragmentMovieBinding
import com.mewz.mymoviebookingapp.ui.activities.MainActivity
import com.mewz.mymoviebookingapp.ui.activities.movie.MovieDetailActivity
import com.mewz.mymoviebookingapp.ui.adapters.movie.BannerAdapter
import com.mewz.mymoviebookingapp.ui.delgates.MovieViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.viewpods.MovieListViewPod
import kotlin.math.abs

class MovieFragment : Fragment(), MovieViewHolderDelegate {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var mBannerAdapter: BannerAdapter

    private lateinit var mNowShowingViewPod: MovieListViewPod
    private lateinit var mComingSoonViewPod: MovieListViewPod

    private var isSelectedTab: Boolean = false

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_movie, container, false)

        binding = FragmentMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAppBarListeners()
        setUpBannerViewPager()
        setUpMovieTabLayout()
        setUpMovieListViewPod()
        requestData()
    }


    private fun requestData() {
        mMovieBookingModel.getBanner(
            onSuccess = {
                mBannerAdapter.setNewData(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )

        mMovieBookingModel.getNowPlayingMovies(
            onSuccess = {
                mNowShowingViewPod.setData(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )

        mMovieBookingModel.getComingSoonMovies(
            onSuccess = {
                mComingSoonViewPod.setData(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )
    }

    private fun setUpMovieListViewPod() {
        mNowShowingViewPod = binding.vpNowShowingMovieList.root
        mNowShowingViewPod.setUpNowShowingListPod(this)

        mComingSoonViewPod = binding.vpComingSoonMovieList.root
        mComingSoonViewPod.setUpComingSoonListPod(this)
    }

    private fun setUpMovieTabLayout() {
        val tab1 = binding.tabLayoutMovie.newTab()
        tab1.text = getString(R.string.movie_tab_now_showing)
        binding.tabLayoutMovie.addTab(tab1)

        val tab2 = binding.tabLayoutMovie.newTab()
        tab2.text = getString(R.string.movie_tab_coming_soon)
        binding.tabLayoutMovie.addTab(tab2)

        binding.tabLayoutMovie.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null){
                    when(tab.position){
                        0 -> {
                            binding.vpNowShowingMovieList.root.visibility = View.VISIBLE
                            binding.vpComingSoonMovieList.root.visibility = View.GONE
                            isSelectedTab = false
                        }
                        1 -> {
                            binding.vpNowShowingMovieList.root.visibility = View.GONE
                            binding.vpComingSoonMovieList.root.visibility = View.VISIBLE
                            isSelectedTab = true
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setUpBannerViewPager() {
        mBannerAdapter = BannerAdapter()
        binding.viewPagerBanner.adapter = mBannerAdapter
        binding.dotsIndicatorBanner.attachTo(binding.viewPagerBanner)

        setUpBannerViewpagerPadding()
        setUpBannerViewpagerTransformer()
    }

    private fun setUpBannerViewpagerTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer((10 * Resources.getSystem().displayMetrics.density).toInt()))
        transformer.addTransformer{ page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.80f + r * 0.20f

        }
        binding.viewPagerBanner.setPageTransformer(transformer)
    }

    private fun setUpBannerViewpagerPadding() {
        binding.viewPagerBanner.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun setUpAppBarListeners() {
        val city = (activity as AppCompatActivity).intent.getStringExtra(MainActivity.EXTRA_REGION)
        binding.tvLocation.text = city

    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(context,error, Toast.LENGTH_LONG).show()
    }

    override fun onTapMovie(movieId: Int) {
        val city = binding.tvLocation.text.toString()
        startActivity(this.context?.let { MovieDetailActivity.newIntent(it,isSelectedTab,movieId,city) })
    }

}