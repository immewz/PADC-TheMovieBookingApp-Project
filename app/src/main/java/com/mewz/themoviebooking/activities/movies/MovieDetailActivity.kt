package com.mewz.themoviebooking.activities.movies

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.activities.MainActivity
import com.mewz.themoviebooking.adapters.CastAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.data.vos.movie.MovieVO
import com.mewz.themoviebooking.databinding.ActivityMovieDetailBinding
import com.mewz.themoviebooking.mvp.presenters.MovieDetailPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.MovieDetailPresenterImpl
import com.mewz.themoviebooking.mvp.views.MovieDetailView
import com.mewz.themoviebooking.network.IMAGE_BASE_URL
import java.time.LocalTime

class MovieDetailActivity : BaseActivity(), MovieDetailView {

    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var mAdapter: CastAdapter

    private lateinit var mPresenter: MovieDetailPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var mMovieId: Int = 0
    private var mMovieName: String? = null

    companion object{
        const val EXTRA_COMING_SOON = "EXTRA_COMING_SOON"
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(context: Context, isComingSoon: Boolean, movieId: Int): Intent{
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_COMING_SOON, isComingSoon)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMovieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)!!
        // Toast.makeText(this, mMovieId, Toast.LENGTH_LONG).show()

        setUpPresenter()
        setUpRecyclerView()
        setUpVisibilityNotificationBoard()
        setUpListeners()

        setUpRequestData(mMovieId)
    }


    private fun setUpPresenter() {
        mPresenter = getPresenter<MovieDetailPresenterImpl, MovieDetailView>()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpRequestData(movieId: Int?) {
        mTheMovieBookingModel.getMovieDetailById(
            movieId.toString(),
            onSuccess = { bindData(it) },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindData(movie: MovieVO) {

        mMovieName = movie.originalTitle

        binding.collapsingToolbar.title = movie.originalTitle

        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(binding.ivMoviePoster)

        binding.tvMovieName.text = movie.originalTitle
        binding.tvRating.text = movie.changeAverageFormat()

        bindGenre(movie)

        binding.tvDate.text = movie.changeReleaseDateFormat("detail")
        binding.tvTime.text = movie.changeRuntimeFormat()

        binding.tvMovieOverView.text = movie.overview

        movie.casts?.let {
            mAdapter.setNewData(it)
        }

    }

    private fun bindGenre(movie: MovieVO) {

        movie.genres?.count()?.let {
            binding.chipGenreOne.text =  movie.genres.firstOrNull() ?: ""
            binding.chipGenreTwo.text =  movie.genres.getOrNull(1) ?: ""
            binding.chipGenreThree.text =  movie.genres.getOrNull(2) ?: ""
            binding.chipGenreFour.text =  movie.genres.getOrNull(3) ?: ""
            binding.chipGenreFive.text =  movie.genres.getOrNull(4) ?: ""

            when(it) {
                0 -> {
                    binding.chipGenreOne.visibility = View.GONE
                    binding.chipGenreTwo.visibility= View.GONE
                    binding.chipGenreThree.visibility= View.GONE
                    binding.chipGenreFour.visibility= View.GONE
                    binding.chipGenreFive.visibility= View.GONE
                }
                1 -> {
                    binding.chipGenreTwo.visibility= View.GONE
                    binding.chipGenreThree.visibility= View.GONE
                    binding.chipGenreFour.visibility= View.GONE
                    binding.chipGenreFive.visibility= View.GONE
                }
                2 -> {
                    binding.chipGenreThree.visibility= View.GONE
                    binding.chipGenreFour.visibility= View.GONE
                    binding.chipGenreFive.visibility= View.GONE
                }
                3 -> {
                    binding.chipGenreFour.visibility= View.GONE
                    binding.chipGenreFive.visibility= View.GONE
                }
                4 -> {
                    binding.chipGenreFive.visibility= View.GONE
                }

            }
        }
    }


    private fun setUpListeners() {

        binding.btnBackMovieDetailScreen.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.btnShare.setOnClickListener {
            Toast.makeText(this, "Click", Toast.LENGTH_LONG).show()
        }

        binding.btnBooking.setOnClickListener {
            mPresenter.onTapBookingButton(mMovieId.toString(), mMovieName.toString())
        }
    }

    private fun setUpVisibilityNotificationBoard() {
        val isComingSoon = intent?.getBooleanExtra(EXTRA_COMING_SOON, false)
        if (isComingSoon == false){
            binding.flNotiReleaseBoard.visibility = View.GONE
            binding.btnBooking.visibility = View.VISIBLE
        }else{
            binding.flNotiReleaseBoard.visibility = View.VISIBLE
            binding.btnBooking.visibility = View.GONE

        }
    }

    private fun setUpRecyclerView() {
        mAdapter = CastAdapter()
        binding.rvCastList.adapter = mAdapter
        binding.rvCastList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun navigateToBackScreen() {
        super.onBackPressed()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun navigateToChooseTimeAndCinemaScreen(movieId: String, movieName: String) {
        startActivity(ChooseTimeAndCinemaActivity.newIntent(this, movieId, movieName))
    }
}