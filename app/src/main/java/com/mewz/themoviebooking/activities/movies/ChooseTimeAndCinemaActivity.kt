package com.mewz.themoviebooking.activities.movies

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.activities.cinema.CinemaInfoActivity
import com.mewz.themoviebooking.adapters.DateCardAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.databinding.ActivityChooseTimeAndCinemaBinding
import com.mewz.themoviebooking.mvp.presenters.ChooseTimeAndCinemaPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.ChooseTimeAndCinemaPresenterImpl
import com.mewz.themoviebooking.mvp.views.ChooseTimeAndCinemaView
import com.mewz.themoviebooking.utils.TimeslotUtil
import com.mewz.themoviebooking.utils.data.CinemaData
import com.mewz.themoviebooking.views.viewpods.CinemaViewPod

@RequiresApi(Build.VERSION_CODES.O)
class ChooseTimeAndCinemaActivity : BaseActivity(), ChooseTimeAndCinemaView {

    private lateinit var binding: ActivityChooseTimeAndCinemaBinding

    private lateinit var mDateCardAdapter: DateCardAdapter

    private lateinit var mViewPod: CinemaViewPod

    private  lateinit var mPresenter: ChooseTimeAndCinemaPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private lateinit var mTimeslotUtil: TimeslotUtil

    private var mBookingData: String? = null
    private var mMovieId: String? = null
    private var mMovieName: String? = null
    private var mCinemaId: Int? = 0
    private var mCinemaName: String? = null
    private var mCinemaLocation: String? = null

    companion object{

        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        const val EXTRA_MOVIE_NAME = "EXTRA_MOVIE_NAME"

         fun newIntent(context: Context, movieId: String, movieName: String): Intent {
             val intent = Intent(context, ChooseTimeAndCinemaActivity::class.java)
             intent.putExtra(EXTRA_MOVIE_ID, movieId)
             intent.putExtra(EXTRA_MOVIE_NAME, movieName)
             return intent
         }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseTimeAndCinemaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMovieId = intent?.getStringExtra(EXTRA_MOVIE_ID) ?: ""
        mMovieName = intent?.getStringExtra(EXTRA_MOVIE_NAME) ?: ""
        // Toast.makeText(this, "$mMovieId $mMovieName", Toast.LENGTH_LONG).show()

        mTimeslotUtil = TimeslotUtil()
        mTimeslotUtil.addTimeslotDataToList()

        setUpPresenter()
        setUpRecyclerView()
        bindTimeslotData()

        setUpViewPod()
        setUpListeners()


    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<ChooseTimeAndCinemaPresenterImpl, ChooseTimeAndCinemaView>()
    }

    private fun setUpRequestData(date: String) {
        mTheMovieBookingModel.getCinemaAndShowtimeByDate(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            date = date,
            onSuccess = { mViewPod.setData(it) },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
    }

    private fun setUpListeners() {
        binding.btnBackChooseTimeAndCinemaScreen.setOnClickListener {
            mPresenter.onTapBackButton()
        }
    }

    private fun setUpViewPod() {
        mViewPod = binding.vpCinemaList.root
        mViewPod.setUpCinemaViewPod(mPresenter)
    }

    private fun bindTimeslotData() {
        mDateCardAdapter.bindData(mTimeslotUtil.dateList)
    }

    private fun setUpRecyclerView() {
        mDateCardAdapter = DateCardAdapter(mTimeslotUtil.dateListTimeslot,mPresenter)
        binding.rvDateCardList.adapter = mDateCardAdapter
        binding.rvDateCardList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun showDateCard(date: String) {
        mBookingData = date
        setUpRequestData(date)
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show()
    }

    override fun showCinemaId(cinemaId: Int?) {
        mCinemaId = cinemaId
        mCinemaId?.let { id ->
            mTheMovieBookingModel.getCinema(id)?.also { cinemaInfo ->
                mCinemaLocation = cinemaInfo.address
            }
        }
        // Toast.makeText(this, cinemaId.toString(), Toast.LENGTH_LONG).show()
    }

    override fun showCinemaName(cinemaName: String?) {
        mCinemaName = cinemaName
        // Toast.makeText(this, cinemaName, Toast.LENGTH_LONG).show()
    }

    override fun navigateToBackScreen() {
        super.onBackPressed()
    }

    override fun navigateToSeatScreen(timeslotId: Int?, cinemaTime: String?) {
        val cinemaInfo = CinemaData(mCinemaName, mBookingData, cinemaTime, mCinemaLocation, timeslotId )
        startActivity(ChooseSeatActivity.newIntent(this, mMovieId.toString(), mMovieName.toString(), cinemaInfo))
    }

    override fun navigateToCinemaInfoScreen(cinemaId: Int?) {
        startActivity(cinemaId?.let { CinemaInfoActivity.newIntent(this, it) })
        Toast.makeText(this, cinemaId.toString(), Toast.LENGTH_LONG).show()
    }
}