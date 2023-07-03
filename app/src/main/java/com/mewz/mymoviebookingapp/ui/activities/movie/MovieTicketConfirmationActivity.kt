package com.mewz.mymoviebookingapp.ui.activities.movie

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutSnackList
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaTimeslotVO
import com.mewz.mymoviebookingapp.data.vos.ticket.TicketInformation
import com.mewz.mymoviebookingapp.databinding.ActivityMovieTicketConfirmationBinding
import com.mewz.mymoviebookingapp.network.utiles.IMAGE_BASE_URL
import com.mewz.mymoviebookingapp.ui.activities.MainActivity
import com.mewz.mymoviebookingapp.ui.utils.TicketData

class MovieTicketConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieTicketConfirmationBinding

    private var mTicketInfo: TicketData? = null
    private var mSnackInfo: CheckoutSnackList? = null
    private var mTicketList: MutableList<String> = mutableListOf()
    private var mCheckoutVO: CheckoutVO? = null
    private var mCinemaTimeslotVO: CinemaTimeslotVO? = null

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object{
        private const val EXTRA_TICKET_INFO = "EXTRA_TICKET_INFO"
        private const val EXTRA_SNACK_INFO = "EXTRA_SNACK_INFO"
        private const val EXTRA_PAYMENT_ID = "EXTRA_PAYMENT_ID"
        fun newIntent(context: Context, ticketInfo: TicketData?, snackInfo: CheckoutSnackList?, paymentId: Int?): Intent{
            val intent = Intent(context,MovieTicketConfirmationActivity::class.java)
            intent.putExtra(EXTRA_TICKET_INFO, ticketInfo)
            intent.putExtra(EXTRA_SNACK_INFO, snackInfo)
            intent.putExtra(EXTRA_PAYMENT_ID, paymentId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMovieTicketConfirmationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mTicketInfo = intent?.getSerializableExtra(EXTRA_TICKET_INFO) as TicketData?
        mSnackInfo = intent?.getSerializableExtra(EXTRA_SNACK_INFO) as CheckoutSnackList?
        mTicketList = mTicketInfo?.seatInfo?.ticketList ?: mutableListOf()

        val date = mTicketInfo?.cinemaInfo?.cinemaData
        val row = getTicketRow()
        val seat = getTicketList()
        val totalSeat = mTicketInfo?.seatInfo?.numberOfTicket
        val totalPrice = mTicketInfo?.snackPrice.toString()
        val movieId = mTicketInfo?.movieInfo?.movieId
        val cinemaId = mTicketInfo?.cinemaInfo?.cinemaId

        val timeslotId = mTicketInfo?.cinemaInfo?.cinemaTimeslotId
        val time = mTicketInfo?.cinemaInfo?.cinemaTime

        mCinemaTimeslotVO = CinemaTimeslotVO(timeslotId,time,null)
        mCheckoutVO = CheckoutVO(null,null,date,row,seat,totalSeat,totalPrice,movieId,cinemaId,null,
        mCinemaTimeslotVO,null,null)

        val address = mTicketInfo?.cinemaInfo?.cinemaLocation.toString()
        val movie = mTicketInfo?.movieInfo?.movieName.toString()
        val poster = mTicketInfo?.movieInfo?.moviePoster.toString()

        mMovieBookingModel.insertTicket(TicketInformation(mCheckoutVO, address, movie, poster))

        setUpListener()
        bindData()
    }

    private fun bindData() {

        binding.tvMovieTitle.text = mTicketInfo?.movieInfo?.movieName
        binding.tvCinema.text = mTicketInfo?.cinemaInfo?.cinemaName
        binding.tvTicketCount.text = mTicketInfo?.seatInfo?.numberOfTicket.toString()
        binding.tvCinemaDate.text = mTicketInfo?.cinemaInfo?.cinemaData
        binding.tvCinemaTime.text = mTicketInfo?.cinemaInfo?.cinemaTime
        binding.tvCinemaLocation.text = mTicketInfo?.cinemaInfo?.cinemaLocation

        val ticketName = getTicketList()
        binding.tvReceiptSeat.text = ticketName

        val moviePoster = mTicketInfo?.movieInfo?.moviePoster
        Glide.with(this)
            .load("$IMAGE_BASE_URL${moviePoster}")
            .into(binding.ivMovie)


    }

    private fun getTicketRow(): String{
        var row = ""
        if (mTicketList.isNotEmpty()){
            mTicketList.forEach {
                row += "$it,"
            }
            row = StringBuilder(row).also {
                it.deleteCharAt(it.lastIndex)
            }.toString()
        }
        return row
    }

    private fun getTicketList(): String {
        var ticket = ""
        if (mTicketList.isNotEmpty()){
            mTicketList.forEach {
                ticket += "$it,"
            }
            ticket = StringBuilder(ticket).also {
                it.deleteCharAt(it.lastIndex)
            }.toString()
        }
        return ticket
    }

    private fun setUpListener() {
        Handler().postDelayed({
            binding.rlEnjoyMovie.visibility = View.INVISIBLE
        }, 3000)

        binding.btnDone.setOnClickListener {
            startActivity(MainActivity.newIntent(this,"Yangon"))
            finish()
        }
    }
}