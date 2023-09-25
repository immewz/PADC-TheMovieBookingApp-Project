package com.mewz.themoviebooking.fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.google.android.material.tabs.TabLayout
import com.mewz.themoviebooking.activities.MainActivity
import com.mewz.themoviebooking.activities.movies.MovieDetailActivity
import com.mewz.themoviebooking.activities.movies.SearchMovieActivity
import com.mewz.themoviebooking.adapters.BannerAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.databinding.FragmentMovieBinding
import com.mewz.themoviebooking.mvp.presenters.MoviePresenter
import com.mewz.themoviebooking.mvp.presenters.impls.MoviePresenterImpl
import com.mewz.themoviebooking.mvp.views.MovieView
import com.mewz.themoviebooking.utils.movie_tab
import com.mewz.themoviebooking.views.viewpods.MovieViewPod

class MovieFragment : Fragment(), MovieView {

    private lateinit var binding: FragmentMovieBinding

    private lateinit var mBannerAdapter: BannerAdapter

    private lateinit var mNowShowingViewPod: MovieViewPod
    private lateinit var mComingSoonViewPod: MovieViewPod

    private lateinit var mPresenter: MoviePresenter

    private lateinit var sharedPrefCity: SharedPreferences

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var isComingSoon: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_movie, container, false)

        binding = FragmentMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefCity = activity?.getSharedPreferences("CITY_PREF", Context.MODE_PRIVATE)!!

        setUpPresenter()

        setUpBannerView()
        setUpTabLayout()
        setUpViewPod()
        setUpListener()

        setUpRequestData()

    }



    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(requireActivity())[MoviePresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpRequestData() {
        val city = (activity as AppCompatActivity).intent.getStringExtra(MainActivity.EXTRA_CITY)
        binding.tvLocation.text = city

        if (city == null){
            val citySharedPref = sharedPrefCity.getString("city", "")
            binding.tvLocation.text = citySharedPref
        }else{
            val editor = sharedPrefCity.edit()
            editor?.putString("city", city)
            editor?.apply()
        }

        mTheMovieBookingModel.getBanners(
            onSuccess = { mBannerAdapter.setNewData(it) },
            onFailure = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        )

        mTheMovieBookingModel.getNowPlayingMovie(
            onSuccess = { mNowShowingViewPod.setDataForNowShowing(it) },
            onFailure = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        )

        mTheMovieBookingModel.getComingSoonMovie(
            onSuccess = { mComingSoonViewPod.setDataForComingSoon(it) },
            onFailure = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
        )

    }

    private fun setUpListener() {
        binding.btnSearchMovie.setOnClickListener {
            startActivity(SearchMovieActivity.newIntent(requireActivity()))
        }
    }

    private fun setUpViewPod() {
        mNowShowingViewPod = binding.vpNowShowingMovieList.root
        mNowShowingViewPod.setUpNowShowingViewPod(mPresenter)

        mComingSoonViewPod = binding.vpComingSoonMovieList.root
        mComingSoonViewPod.setUpComingSoonViewPod(mPresenter)
    }

    private fun setUpTabLayout() {
        movie_tab.forEach{
            binding.tabLayoutShowingOrComing.newTab().apply {
                text = it
                binding.tabLayoutShowingOrComing.addTab(this)
            }
        }

        binding.tabLayoutShowingOrComing.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null){
                    when (tab.position) {
                        0 -> {
                            binding.vpNowShowingMovieList.root.visibility = View.VISIBLE
                            binding.vpComingSoonMovieList.root.visibility = View.GONE
                            isComingSoon = false
                        }
                        1 -> {
                            binding.vpNowShowingMovieList.root.visibility = View.GONE
                            binding.vpComingSoonMovieList.root.visibility = View.VISIBLE
                            isComingSoon = true
                        }
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun setUpBannerView() {
        setUpBannerViewPagerPadding()
        setUpRecyclerView()
        setUpBannerTransformer()
    }

    private fun setUpBannerTransformer() {
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(
            MarginPageTransformer((10 * Resources.getSystem().displayMetrics.density).toInt())
        )
        compositePageTransformer.addTransformer{ page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.viewPagerBanner.setPageTransformer(compositePageTransformer)
    }

    private fun setUpRecyclerView() {
        mBannerAdapter = BannerAdapter()
        binding.viewPagerBanner.adapter = mBannerAdapter
        binding.dotsIndicator.attachTo(binding.viewPagerBanner)
    }

    private fun setUpBannerViewPagerPadding() {
        binding.viewPagerBanner.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        }
    }

    override fun navigateToMovieDetailScreen(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(requireActivity(), isComingSoon, movieId))
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}