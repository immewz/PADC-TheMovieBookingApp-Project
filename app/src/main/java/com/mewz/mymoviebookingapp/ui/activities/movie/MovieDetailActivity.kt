package com.mewz.mymoviebookingapp.ui.activities.movie

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.movie.MovieVO
import com.mewz.mymoviebookingapp.databinding.ActivityMovieDetailBinding
import com.mewz.mymoviebookingapp.network.utiles.IMAGE_BASE_URL
import com.mewz.mymoviebookingapp.ui.utils.MovieData
import com.mewz.mymoviebookingapp.ui.viewpods.CastListViewPod

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var mCastListViewPod: CastListViewPod

    private var isPlayingMovie: Boolean = false

    private var mMovieName: String? = null
    private var mMovieId: Int = 0
    private var mMoviePoster: String? = null
    private var mMovieCity: String? = null

    private var mMovieInfo: MovieData? = null

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object{
        private const val EXTRA_NOW_SHOWING_OR_COMING_SOON = "EXTRA_NOW_SHOWING_OR_COMING_SOON"
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        private const val EXTRA_CITY = "EXTRA_CITY"
        fun newIntent(context: Context, isNowShowingOrComingSoon: Boolean,movieId: Int, city: String): Intent{
            val intent = Intent(context,MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_NOW_SHOWING_OR_COMING_SOON,isNowShowingOrComingSoon)
            intent.putExtra(EXTRA_MOVIE_ID,movieId)
            intent.putExtra(EXTRA_CITY,city)
            return intent
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAppBarListener()
        setUpCastViewPod()

        setUpMovieTrailer()
        setUpPlayOrPauseMovieTrailer()

        setUpNowShowingOrComingSoon()

        setUpBookingBottom()

        mMovieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)!!
        requestData(mMovieId)

        mMovieCity = intent?.getStringExtra(EXTRA_CITY)
//        Toast.makeText(this,"$mMovieCity",Toast.LENGTH_LONG).show()



    }


    private fun requestData(movieId: Int) {
        mMovieBookingModel.getMovieDetailById(
            movieId = movieId.toString(),
            onSuccess = {
                mMovieName = it.originalTitle
                mMoviePoster = it.posterPath
                mMovieInfo = MovieData(mMovieId,mMovieName,mMoviePoster)
                //Toast.makeText(this,"$mMovieInfo",Toast.LENGTH_LONG).show()
                //Log.d("MD","requestData.${mMovieName}")
                bindData(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )
    }

    private fun bindData(movie: MovieVO) {
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(binding.ivMovie)

        binding.collapsingTitle.title = movie.originalTitle ?: ""
        binding.tvMovieTitle.text = movie.originalTitle ?: ""

        movie.genres?.forEach { genre ->
            binding.chipGenre.addView(createTagChip(applicationContext, genre))
        }

        binding.tvReleaseDate.text = movie.releaseDate ?: ""
        binding.tvDescription.text = movie.overview ?: ""

        movie.casts?.let { mCastListViewPod.setData(it)}
    }

    private fun createTagChip(context: Context, genre: String): View {
        return Chip(this).apply {
            text = genre
            setChipBackgroundColorResource(R.color.green)
            setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }


    private fun setUpNowShowingOrComingSoon() {
        val boolean = intent?.getBooleanExtra(EXTRA_NOW_SHOWING_OR_COMING_SOON,false)
        if (boolean == false){
            binding.rlReleasingNoti.visibility = View.GONE
            binding.btnBooking.visibility = View.VISIBLE
        }else{
            binding.rlReleasingNoti.visibility = View.VISIBLE
            binding.btnBooking.visibility = View.GONE
        }
    }


    private fun setUpPlayOrPauseMovieTrailer() {
        binding.btnPlayOrPause.setOnClickListener {
            if(!isPlayingMovie){
                binding.ivPlay.visibility = View.GONE
                binding.ivPause.visibility = View.VISIBLE
                binding.vvMovieTrailer.start()
                isPlayingMovie = true
            }else{
                binding.ivPlay.visibility = View.VISIBLE
                binding.ivPause.visibility = View.GONE
                binding.vvMovieTrailer.pause()
                isPlayingMovie = false
            }
        }
    }

    private fun setUpMovieTrailer() {
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.vvMovieTrailer)

        val videoUri = Uri.parse("android.resource://com.mewz.mymoviebookingapp/${R.raw.movie}")
        binding.vvMovieTrailer.setMediaController(mediaController)
        binding.vvMovieTrailer.setVideoURI(videoUri)
        binding.vvMovieTrailer.requestFocus()

    }

    private fun setUpCastViewPod() {
        mCastListViewPod = binding.vpCastList.root
        mCastListViewPod.setUpCastListPod()
    }

    private fun setUpAppBarListener() {
        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpBookingBottom() {
        binding.btnBooking.setOnClickListener {
            startActivity(MovieCinemaDataAndTimeActivity.newIntent(this,mMovieCity.toString(),mMovieInfo))
            finish()
        }
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
    }
}