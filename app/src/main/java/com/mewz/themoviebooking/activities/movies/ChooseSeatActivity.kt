package com.mewz.themoviebooking.activities.movies

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.adapters.SeatAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.data.vos.cinema.SeatVO
import com.mewz.themoviebooking.databinding.ActivityChooseSeatBinding
import com.mewz.themoviebooking.mvp.presenters.ChooseSeatPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.ChooseSeatPresenterImpl
import com.mewz.themoviebooking.mvp.views.ChooseSeatView
import com.mewz.themoviebooking.utils.data.CinemaData
import com.mewz.themoviebooking.utils.data.SeatData

@RequiresApi(Build.VERSION_CODES.O)
class ChooseSeatActivity : BaseActivity(), ChooseSeatView {

    private lateinit var binding: ActivityChooseSeatBinding

    private lateinit var mAdapter: SeatAdapter

    private lateinit var mPresenter: ChooseSeatPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var mMovieId: String? = null
    private var mMovieName: String? = null
    private var mCinemaInfo: CinemaData? = null
    private var mCinemaTimeslotId: Int? = 0
    private var mCinemaName: String? = null
    private var mBookingDate: String? = null
    private var mSeatInfo: SeatData? = null

    private var mSeatDoubleList: MutableLiveData<MutableList<MutableList<SeatVO>>> = MutableLiveData<MutableList<MutableList<SeatVO>>>()
    private lateinit var mSeatTicketList: MutableList<String>
    private lateinit var mSeatTicketPrice: MutableList<Int>


    companion object{

        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        const val EXTRA_MOVIE_NAME = "EXTRA_MOVIE_NAME"
        const val EXTRA_CINEMA_INFO = "EXTRA_CINEMA_INFO"

        fun newIntent(context: Context, movieId: String, movieName: String, cinemaInfo: CinemaData?): Intent{
            val intent = Intent(context, ChooseSeatActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            intent.putExtra(EXTRA_MOVIE_NAME, movieName)
            intent.putExtra(EXTRA_CINEMA_INFO, cinemaInfo)
            return intent
        }

        private val seatVO = SeatVO(null, "path", null ,null, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseSeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMovieId = intent?.getStringExtra(EXTRA_MOVIE_ID) ?: ""
        mMovieName = intent?.getStringExtra(EXTRA_MOVIE_NAME) ?: ""
        mCinemaInfo = intent?.getSerializableExtra(EXTRA_CINEMA_INFO) as CinemaData?
        mCinemaTimeslotId = mCinemaInfo?.cinemaTimeslotId
        mCinemaName = mCinemaInfo?.cinemaName
        mBookingDate = mCinemaInfo?.date
        // Toast.makeText(this, "$mMovieId $mMovieName $mCinemaInfo", Toast.LENGTH_LONG).show()
        Log.d("ChooseSeat", "data.{$mMovieId $mMovieName $mCinemaInfo}")

        mSeatTicketList = mutableListOf()
        mSeatTicketPrice = mutableListOf()


        setUpPresenter()
        setUpRecyclerView()
        setUpListeners()
        setUpRequestData()
    }


    private fun setUpPresenter() {
        mPresenter = getPresenter<ChooseSeatPresenterImpl, ChooseSeatView>()
    }

    private fun setUpRequestData() {
        mTheMovieBookingModel.getSeatingPlanByShowTime(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            timeslotId = mCinemaTimeslotId.toString(),
            bookingDate = mBookingDate.toString(),
            onSuccess = { seatDoubleList ->

                mSeatDoubleList.value = seatDoubleList

                val seatList = addCinemaPath(seatDoubleList)
                mAdapter.setNewData(seatList)

                setUpTotalTicketPrice()
            },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
    }

    private fun setUpTotalTicketPrice() {
        val ticketQuantity = "${mSeatTicketList.size} Tickets"
        val ticketTotalPrice = "${mSeatTicketList.size * 2 } Ks"

        binding.tvSeatTicketCount.text = ticketQuantity
        binding.tvSeatTicketPrice.text = ticketTotalPrice
    }

    private fun addCinemaPath(seatDoubleList: MutableList<MutableList<SeatVO>>): MutableList<MutableList<SeatVO>> {
        val newSeatDoubleList: MutableList<MutableList<SeatVO>> = mutableListOf()
        for (i in seatDoubleList.indices) {
            seatDoubleList[i].add(5, seatVO)
            seatDoubleList[i].add(6, seatVO)
            seatDoubleList[i].add(11, seatVO)
            seatDoubleList[i].add(12, seatVO)
            val newSeatSingleList: MutableList<SeatVO> = mutableListOf()
            for (ii in seatDoubleList[i].indices) {
                if(ii == 5 || ii == 6 || ii == 11 || ii == 12) {
                    newSeatSingleList.add(ii, seatVO)
                }else{
                    newSeatSingleList.add(ii,seatDoubleList[i][ii])
                }
            }
            newSeatDoubleList.add(newSeatSingleList)
        }
        return newSeatDoubleList
    }

    private fun setUpListeners() {

        binding.btnBackChooseSeatScreen.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val zoomLevel = (progress / 100f) * (binding.zoomLayout.getMaxZoom() - binding.zoomLayout.getMinZoom() + binding.zoomLayout.getMinZoom() )
                binding.zoomLayout.zoomTo(zoomLevel, false)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.btnBuyTicket.setOnClickListener {
            mPresenter.onTapBuyTicket(mMovieId.toString(), mMovieName.toString(), mCinemaInfo, mSeatInfo)
        }
    }

    private fun setUpRecyclerView() {
        mAdapter = SeatAdapter(mPresenter)
        binding.rvSeatList.adapter = mAdapter
        binding.rvSeatList.layoutManager =
            GridLayoutManager(this, 18, GridLayoutManager.VERTICAL, false)
    }

    override fun showSelectedSeat(seatName: String, isAvailable: Boolean?) {
        mSeatDoubleList.observe(this){ seatDoubleList ->
            outer@ for(seatSingleList in seatDoubleList){
                for (seatVO in seatSingleList) {
                    if (seatVO.seatName == seatName) {
                        if (isAvailable == false) {
                            seatVO.isSelected = true
                            Toast.makeText(this, "Selected ${seatVO.seatName}, ${seatVO.price}", Toast.LENGTH_LONG).show()
                            mSeatTicketList.add(seatVO.seatName)
                            mSeatTicketPrice.add(seatVO.price ?: 0)
                        }else {
                            seatVO.isSelected = false
                            Toast.makeText(this, "Unselected ${seatVO.seatName}, ${seatVO.price}", Toast.LENGTH_LONG).show()
                            mSeatTicketList.remove(seatVO.seatName)
                            mSeatTicketPrice.remove(seatVO.price ?: 0)
                        }
                        break@outer
                    }
                }
            }
             mAdapter.setNewData(seatDoubleList)
        }
        setUpTotalTicketPrice()
    }

    override fun navigateToBackScreen() {
        super.onBackPressed()
    }


    override fun navigateToChooseSnackScreen(
        movieId: String,
        movieName: String,
        cinemaInfo: CinemaData?,
        seatInfo: SeatData?
    ) {
        mSeatInfo = SeatData(mSeatTicketList.size, mSeatTicketList, mSeatTicketList.size * mSeatTicketPrice.size)
        startActivity(ChooseSnackActivity.newIntent(this, mMovieId.toString(), mMovieName.toString(), mCinemaInfo, mSeatInfo))
    }
}