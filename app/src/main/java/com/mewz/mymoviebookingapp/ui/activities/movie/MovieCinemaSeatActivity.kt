package com.mewz.mymoviebookingapp.ui.activities.movie

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.movie.SeatVO
import com.mewz.mymoviebookingapp.databinding.ActivityMovieCinemaSeatBinding
import com.mewz.mymoviebookingapp.ui.adapters.movie.MovieSeatAdapter
import com.mewz.mymoviebookingapp.ui.delgates.SeatViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.utils.CinemaData
import com.mewz.mymoviebookingapp.ui.utils.MovieData
import com.mewz.mymoviebookingapp.ui.utils.SeatData
import com.otaliastudios.zoom.ZoomLayout

class MovieCinemaSeatActivity : AppCompatActivity(), SeatViewHolderDelegate {

    private lateinit var binding: ActivityMovieCinemaSeatBinding
    private lateinit var mMovieSeatAdapter: MovieSeatAdapter

    private var mMovieInfo: MovieData? = null
    private var mCinemaInfo: CinemaData? = null
    private var mSeatInfo: SeatData? = null

    private var mCinemaTimeslotId: Int? = null
    private var mBookingDate: String? = null

    private var mTicketPrice: Int = 0

    private var mSeatList: MutableLiveData<MutableList<MutableList<SeatVO>>> = MutableLiveData<MutableList<MutableList<SeatVO>>>()
    private lateinit var mSeatTicketList: MutableList<String>

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object{
        private const val EXTRA_MOVIE_INFO = "EXTRA_MOVIE_INFO"
        private const val EXTRA_CINEMA_INFO = "EXTRA_CINEMA_INFO"
        fun newIntent(context: Context, movieInfo: MovieData?, cinemaInfo: CinemaData?): Intent {
            val intent = Intent(context,MovieCinemaSeatActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_INFO, movieInfo)
            intent.putExtra(EXTRA_CINEMA_INFO, cinemaInfo)
            return intent
        }

        private val seatVO = SeatVO(null,"path",null,null,null,false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieCinemaSeatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mSeatTicketList = mutableListOf()

        mMovieInfo = intent?.getSerializableExtra(EXTRA_MOVIE_INFO) as MovieData?
        mCinemaInfo = intent?.getSerializableExtra(EXTRA_CINEMA_INFO) as CinemaData?
        mCinemaInfo?.let {
            mCinemaTimeslotId = it.cinemaTimeslotId
            mBookingDate = it.cinemaData
        }
        Log.d("CinemaSeat","dataInfo.${mCinemaInfo},${mMovieInfo}")

        setUpSeatRecyclerView()
        setUpButtonListeners()
        setUpSeekBar()
        requestSeatData()
    }


    private fun setUpTicketPriceAndCount() {
        val ticketCount = "${mSeatTicketList.count()}"
        val ticketPrice = mSeatTicketList.size * mTicketPrice

        binding.tvTicketCount.text = ticketCount
        binding.tvTicketAmount.text = ticketPrice.toString()
    }


    private fun requestSeatData() {
        mCinemaTimeslotId?.let {
            mMovieBookingModel.getSeatingPlan(
                authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
                timeslotId = it,
                bookingDate = mBookingDate.toString(),
                onSuccess = {Seat ->
                    mSeatList.value = Seat

                    val seatList = addCinemaPath(Seat)
                    mMovieSeatAdapter.setNewData(seatList)

                    setUpTicketPriceAndCount()
                },
                onFailure = { it ->
                    showErrorMessage(it)
                }
            )
        }
    }

    private fun addCinemaPath(emptySeat: MutableList<MutableList<SeatVO>>): MutableList<MutableList<SeatVO>> {
        val newSeatList:MutableList<MutableList<SeatVO>> = mutableListOf()
        for (x in emptySeat.indices) {
            emptySeat[x].add(5, seatVO)
            emptySeat[x].add(6, seatVO)
            emptySeat[x].add(11, seatVO)
            emptySeat[x].add(12, seatVO)
            val newSeatSingleList:MutableList<SeatVO> = mutableListOf()
            for (y in emptySeat[x].indices) {
                if(y == 5 || y == 6 || y == 11 || y == 12) {
                    newSeatSingleList.add(y, seatVO)
                }else{
                    newSeatSingleList.add(y,emptySeat[x][y])
                }
            }
            newSeatList.add(newSeatSingleList)
        }
        return newSeatList
    }

    private fun setUpSeatRecyclerView() {
        mMovieSeatAdapter = MovieSeatAdapter(this)
        binding.rvSeatList.adapter = mMovieSeatAdapter
        binding.rvSeatList.layoutManager = GridLayoutManager(this,
        18,GridLayoutManager.VERTICAL,false)
    }


    override fun onTapSeat(seatName: String, seatPrice: Int, isAvailable: Boolean?) {
        mSeatList.observe(this) { seatList ->
            outer@ for (seatSingleList in seatList) {
                for (seatVO in seatSingleList) {
                    if (seatVO.seatName == seatName) {
                        if(isAvailable == false) {
                            mTicketPrice = seatPrice
                            seatVO.selectedSeat = true
                            Toast.makeText(this, "Selected ${seatVO.seatName}", Toast.LENGTH_SHORT).show()
                            mSeatTicketList.add(seatVO.seatName)
                        } else {
                            seatVO.selectedSeat = false
                            Toast.makeText(this, "Unselected ${seatVO.seatName}", Toast.LENGTH_SHORT).show()
                            mSeatTicketList.remove(seatVO.seatName)
                        }
                        break@outer
                    }
                }
            }
            mMovieSeatAdapter.setNewData(seatList)
        }
        setUpTicketPriceAndCount()
    }


    private fun setUpSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, user: Boolean) {
                val zoomLayout = findViewById<ZoomLayout>(R.id.zoomLayoutSeat)
                val zoomProgress = (progress/100f) * (zoomLayout.getMaxZoom() - zoomLayout.getMinZoom()) + zoomLayout.getMinZoom()
                zoomLayout.zoomTo(zoomProgress, false)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun setUpButtonListeners() {
        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnBuyTicket.setOnClickListener {
            mSeatInfo = SeatData(mSeatTicketList.size,mSeatTicketList,(mSeatTicketList.size * mTicketPrice.toLong()))
            startActivity(MovieSnackActivity.newIntent(this,mMovieInfo,mCinemaInfo,mSeatInfo))
            finish()
//            Log.d("CinemaDataTime","seatInfo.$mSeatInfo")
        }
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
    }


}