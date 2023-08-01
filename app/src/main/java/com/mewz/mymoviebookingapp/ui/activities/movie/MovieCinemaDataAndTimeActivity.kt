package com.mewz.mymoviebookingapp.ui.activities.movie

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaVO
import com.mewz.mymoviebookingapp.databinding.ActivityMovieCinemaDataAndTimeBinding
import com.mewz.mymoviebookingapp.ui.adapters.movie.MovieCinemaAdapter
import com.mewz.mymoviebookingapp.ui.adapters.movie.MovieDateCardAdapter
import com.mewz.mymoviebookingapp.ui.delgates.CinemaDetailViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.utils.CinemaData
import com.mewz.mymoviebookingapp.ui.utils.DateCardUtil
import com.mewz.mymoviebookingapp.ui.utils.MovieData

@RequiresApi(Build.VERSION_CODES.O)
class MovieCinemaDataAndTimeActivity : AppCompatActivity(), CinemaDetailViewHolderDelegate{

    private lateinit var binding: ActivityMovieCinemaDataAndTimeBinding

    private lateinit var mMovieDateCardAdapter: MovieDateCardAdapter
    private lateinit var mMovieCinemaAdapter: MovieCinemaAdapter
    private lateinit var mDateCardUtil: DateCardUtil
    private var mCinemaList:List<CinemaVO> = listOf()

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    private var mMovieCity: String? = null
    private var mMovieInfo: MovieData? = null

    private var mCinemaId: Int? = null
    private var mCinemaName: String? = null
    private var mBookingDate: String? = null
    private var mCinemaTime: String? = null
    private var mCinemaTimeslotId: Int? = null
    private var mCinemaInfo: CinemaData? = null
    private var mCinemaLocation: String? = null

    companion object{
        private const val EXTRA_CITY = "EXTRA_CITY"
        private const val EXTRA_MOVIE_INFO = "EXTRA_MOVIE_INFO"
        fun newIntent(context: Context,city: String, movieInfo: MovieData?): Intent {
            val intent = Intent(context,MovieCinemaDataAndTimeActivity::class.java)
            intent.putExtra(EXTRA_CITY,city)
            intent.putExtra(EXTRA_MOVIE_INFO,movieInfo)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieCinemaDataAndTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMovieCity = intent?.getStringExtra(EXTRA_CITY)

        mDateCardUtil = DateCardUtil()
        mDateCardUtil.addTimeSlotTODateList()

        setUpAppBarListeners()
        setUpRecyclerViews()

        setUpDateCardData()

        mMovieInfo = intent?.getSerializableExtra(EXTRA_MOVIE_INFO) as MovieData?
//        Toast.makeText(this,"$mMovieInfo",Toast.LENGTH_LONG).show()

    }

    private fun setUpDateCardData() {
        mMovieDateCardAdapter.bindDateCardData(mDateCardUtil.dateList)
    }

    private fun setUpRecyclerViews() {
        mMovieDateCardAdapter = MovieDateCardAdapter(mDateCardUtil.dateListDateCard,this)
        binding.rvDateCardList.adapter = mMovieDateCardAdapter
        binding.rvDateCardList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL,false)

        mMovieCinemaAdapter = MovieCinemaAdapter(this)
        binding.rvCinemaList.adapter = mMovieCinemaAdapter
        binding.rvCinemaList.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
    }

    private fun setUpAppBarListeners() {
        binding.tvLocation.text = mMovieCity

        binding.btnBackChooseView.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onTapSeeDetail(cinemaId: Int) {

    }

    override fun onTapDateCard(bookingDate: String) {
        mBookingDate = bookingDate
        mMovieBookingModel.getCinemaAndTimeslot(
            authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
            date = bookingDate,
            onSuccess = {
                mMovieCinemaAdapter.setNewData(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )
    }

    override fun onTapTimeslot(cinemaTimeslotId: Int, cinemaTime: String) {
        mCinemaTimeslotId = cinemaTimeslotId
        mCinemaTime = cinemaTime
        mCinemaInfo = CinemaData(mCinemaId,mCinemaName,mBookingDate,mCinemaTime,mCinemaTimeslotId,mCinemaLocation)
        Log.d("CinemaDataTime","dataInfo.${mCinemaInfo},${mMovieInfo}")
        startActivity(MovieCinemaSeatActivity.newIntent(this,mMovieInfo,mCinemaInfo))
        finish()
    }

    override fun getCinemaName(cinemaName: String?) {
        mCinemaName = cinemaName
    }

    override fun getCinemaId(cinemaId: Int?) {
        mCinemaId = cinemaId
        mCinemaId?.let { id ->
            mMovieBookingModel.getCinemaInfo(id)?.let {
                mCinemaLocation = it.address
            }
        }

    }

    override fun onClickCinema(cinemaId: Int?) {
        mCinemaList.forEach {
            it.isClicked = cinemaId == it.cinemaId
        }
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
    }


}