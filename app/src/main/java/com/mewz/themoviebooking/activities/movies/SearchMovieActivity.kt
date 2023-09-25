package com.mewz.themoviebooking.activities.movies

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.databinding.ActivitySearchMovieBinding
import com.mewz.themoviebooking.delegates.MovieViewHolderDelegate
import com.mewz.themoviebooking.views.viewpods.MovieViewPod

class SearchMovieActivity : AppCompatActivity(), MovieViewHolderDelegate {

    private lateinit var binding: ActivitySearchMovieBinding

    private lateinit var mNowShowingViewPod: MovieViewPod

    companion object{
        fun newIntent(context: Context): Intent{
            return Intent(context, SearchMovieActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewPod()
        setUpListeners()
    }

    private fun setUpListeners() {

    }

    private fun setUpViewPod() {
        mNowShowingViewPod = binding.vpNowShowingMovieList.root
        mNowShowingViewPod.setUpNowShowingViewPod(this)

    }

    override fun onTapMovie(movieId: Int) {
    }
}