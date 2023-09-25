package com.mewz.themoviebooking.activities.movies

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.activities.MainActivity
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutTicketVO
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.databinding.ActivityTicketInformationBinding
import com.mewz.themoviebooking.mvp.presenters.TicketInformationPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.TicketInformationPresenterImpl
import com.mewz.themoviebooking.mvp.views.TicketInformationView
import com.mewz.themoviebooking.network.BASE_URL
import com.mewz.themoviebooking.network.IMAGE_BASE_URL
import com.mewz.themoviebooking.utils.data.TicketData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class TicketInformationActivity : BaseActivity(), TicketInformationView {

    private lateinit var binding: ActivityTicketInformationBinding

    private lateinit var mPresenter: TicketInformationPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private val SPLAH_TIME = 2000L

    private var mCheckoutTicket: CheckoutTicketVO? = null
    private var mTicketInfo: TicketData? = null
    private var mPaymentId: Int = 0
    private var mMovieId: Int? = 0
    private var mMovieName: String? = null
    private var mMoviePoster: String? = null
    private var mCinemaId: Int? = 0
    private var mCinemaName: String? = null
    private var mCinemaDate: String? = null
    private var mCinemaTime: String? = null
    private var mCinemaAddress: String? = null
    private var mNumberOfTicket: String? = null
    private var mTicketList: String? = null

    companion object{

        const val EXTRA_PAYMENT_ID = "EXTRA_PAYMENT_ID"
        const val EXTRA_TICKET_INFO = "EXTRA_TICKET_INFO"
        const val EXTRA_CHECKOUT_TICKET = "EXTRA_CHECKOUT_TICKET"

        fun newIntent(context: Context, paymentId: Int, ticketInfo: TicketData?, checkoutTicket: CheckoutTicketVO?): Intent{
            val intent = Intent(context, TicketInformationActivity::class.java)
            intent.putExtra(EXTRA_PAYMENT_ID, paymentId)
            intent.putExtra(EXTRA_TICKET_INFO, ticketInfo)
            intent.putExtra(EXTRA_CHECKOUT_TICKET, checkoutTicket)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCheckoutTicket = intent?.getSerializableExtra(EXTRA_CHECKOUT_TICKET) as CheckoutTicketVO
        mTicketInfo = intent?.getSerializableExtra(PaymentActivity.EXTRA_TICKET_INFO) as TicketData
        mPaymentId = intent?.getIntExtra(EXTRA_PAYMENT_ID, 0) ?: 0
        Log.d("TicketConfirmationData","data.$mCheckoutTicket $mPaymentId")

        mMovieId = mCheckoutTicket?.movie_id
        mCinemaId = mCheckoutTicket?.cinema_id
        mCinemaDate = mCheckoutTicket?.booking_date
        mCinemaTime = mCheckoutTicket?.timeslot?.startTime
        mNumberOfTicket = mCheckoutTicket?.total_seat.toString()
        mTicketList = mCheckoutTicket?.seat


        setUpPresenter()
        setUpListener()
        setUpBindData()
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<TicketInformationPresenterImpl, TicketInformationView>()
    }

    private fun setUpBindData() {

        val movie = mTheMovieBookingModel.getMovieForTicketById(mMovieId.toString())
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie?.posterPath}")
            .into(binding.ivMoviePoster)
        mMovieName = movie?.originalTitle ?: ""
        mMoviePoster = movie?.posterPath ?: ""
        binding.tvMovieName.text = mMovieName

        mCinemaId?.let { id ->
            mTheMovieBookingModel.getCinema(id)?.also { cinemaInfo ->
                mCinemaName = cinemaInfo.name
                mCinemaAddress = cinemaInfo.address
                binding.tvCinemaName.text = mCinemaName
                binding.tvPlace.text = mCinemaAddress
            }
        }

        binding.tvTicketCount.text = mNumberOfTicket
        binding.tvSeat.text = mTicketList

        binding.tvDate.text = changeBookingDate()
        binding.tvTime.text = mCinemaTime
        binding.tvPlace.text = mCinemaAddress

        Glide.with(this)
            .load("$BASE_URL/${mCheckoutTicket?.qrCode}")
            .into(binding.ivQrCode)

        mTheMovieBookingModel.insertTicket(getTicketInformation())
    }

    private fun getTicketInformation(): TicketInformation {
        return TicketInformation(
            mCheckoutTicket,
            mTicketInfo?.snackList,
            mMovieName,
            mMoviePoster,
            mCinemaName,
            mCinemaAddress
        )
    }


    private fun changeBookingDate(): String{
        val date = getBookingDateInstance()
        val day = date.dayOfMonth.toString()
        val dayOfWeek = date.dayOfWeek.toString()
        val month = date.month.toString()

        val dayOfWeekWithThreeCharacter = getDayOfWeekWithThreeCharacter(dayOfWeek)
        val monthWithThreeCharacter = getMonthThreeCharacter(month)

        return "$dayOfWeekWithThreeCharacter, $day $monthWithThreeCharacter, ${date.year}"
    }

    private fun getMonthThreeCharacter(month: String): String {
        var monthStr = String()
        for ( letter in 0..2) {
            monthStr += month[letter]
        }
        return monthStr
    }

    private fun getDayOfWeekWithThreeCharacter(dayOfWeek: String): String {
        var dayOfWeekStr = String()
        for ( letter in 0..2){
            dayOfWeekStr += dayOfWeek[letter]
        }
        return dayOfWeekStr
    }

    private fun getBookingDateInstance(): LocalDate {
        val bookingDateString = mCinemaDate?.replace('-','/')
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.ENGLISH)
        return LocalDate.parse(bookingDateString, dateFormatter)
    }

    private fun setUpListener() {

        Handler().postDelayed({
            binding.ivEnjoyMovie.visibility = View.GONE
        }, SPLAH_TIME)

        binding.btnBackTicketInformationScreen.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.btnDone.setOnClickListener {
            mPresenter.onTapDoneButton()
        }
    }

    override fun navigateToBackScreen() {
        super.onBackPressed()
    }

    override fun navigateToMainScreen() {
        startActivity(MainActivity.newIntent(this, ""))
    }
}