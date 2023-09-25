package com.mewz.themoviebooking.activities.movies

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.activities.login.LoginActivity
import com.mewz.themoviebooking.adapters.SnackTicketAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutSnackList
import com.mewz.themoviebooking.data.vos.snack.SnackVO
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.databinding.ActivityCheckoutBinding
import com.mewz.themoviebooking.dialog.CancellationPolicyFragment
import com.mewz.themoviebooking.mvp.presenters.CheckoutPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.CheckoutPresenterImpl
import com.mewz.themoviebooking.mvp.views.CheckoutView
import com.mewz.themoviebooking.utils.data.CinemaData
import com.mewz.themoviebooking.utils.data.SeatData
import com.mewz.themoviebooking.utils.data.TicketData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class CheckoutActivity : BaseActivity(), CheckoutView {

    private lateinit var binding: ActivityCheckoutBinding

    private lateinit var mAdapter: SnackTicketAdapter

    private lateinit var mPresenter: CheckoutPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var isClickedSnackList: Boolean = false
    private var mTicketInfo: TicketData? = null
    private var mSnackInfo: CheckoutSnackList? = null
    private var mMovieId: String? = null
    private var mMovieName: String? = null
    private var mCinemaName: String? = null
    private var mCinemaDate: String? = null
    private var mCinemaTime: String? = null
    private var mCinemaAddress: String? = null
    private var mNumberOfTicket: String? = null
    private var mTotalTicketPrice: Int? = 0
    private var mTotalSnackPrice: Int? = 0
    private var mTicketList: MutableList<String> = mutableListOf()
    private lateinit var mSnackList: MutableList<SnackVO>
    private var mTicket: TicketInformation? = null

    private var mFinalTicketInfo: TicketData? = null

    companion object{

        const val EXTRA_WHERE_SNACK_OR_TICKET_SCREEN = "EXTRA_WHERE_SNACK_OR_TICKET_SCREEN"
        const val EXTRA_TICKET_INFO = "EXTRA_TICKET_INFO"
        const val EXTRA_SNACK_INFO = "EXTRA_SNACK_INFO"
        const val EXTRA_TICKET = "EXTRA_TICKET"

        fun newIntent(context: Context, screen: String, ticketInfo: TicketData?, snackInfo: CheckoutSnackList?, ticket: TicketInformation?): Intent{
            val intent = Intent(context, CheckoutActivity::class.java)
            intent.putExtra(EXTRA_WHERE_SNACK_OR_TICKET_SCREEN, screen)
            intent.putExtra(EXTRA_TICKET_INFO, ticketInfo)
            intent.putExtra(EXTRA_SNACK_INFO, snackInfo)
            intent.putExtra(EXTRA_TICKET, ticket)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val screen = intent?.getStringExtra(EXTRA_WHERE_SNACK_OR_TICKET_SCREEN) ?: ""
        mTicketInfo = intent?.getSerializableExtra(EXTRA_TICKET_INFO) as TicketData
        mSnackInfo = intent?.getSerializableExtra(EXTRA_SNACK_INFO) as CheckoutSnackList
        mTicket = intent?.getSerializableExtra(EXTRA_TICKET) as TicketInformation
        Log.d("CheckoutData","data.$mTicketInfo, $mSnackInfo")

        mMovieId = mTicketInfo?.movieId
        mMovieName = mTicketInfo?.movieName
        mCinemaName = mTicketInfo?.cinemaInfo?.cinemaName
        mCinemaDate = mTicketInfo?.cinemaInfo?.date
        mCinemaTime = mTicketInfo?.cinemaInfo?.time
        mCinemaAddress = mTicketInfo?.cinemaInfo?.address
        mNumberOfTicket = mTicketInfo?.seatInfo?.numberOfTicket.toString()
        mTicketList = mTicketInfo?.seatInfo?.ticketList ?: mutableListOf()
        mTotalTicketPrice = mTicketInfo?.seatInfo?.ticketTotalPrice
        mTotalSnackPrice = mTicketInfo?.snackPrice?.toInt()

        mSnackList = mutableListOf()

        setUpPresenter()
        setUpRecyclerView()
        setUpListeners()
        setUpChangeScreen(screen)
    }


    private fun setUpPresenter() {
        mPresenter = getPresenter<CheckoutPresenterImpl, CheckoutView>()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpChangeScreen(screen: String) {

        if (screen == "Checkout"){
            binding.tvCheckout.text = "Checkout"

            bindCheckoutTicketData()
        }else{
            binding.tvCheckout.text = "Ticket Detail"
            binding.btnContinue.visibility = View.GONE
            binding.llCancelBooking.visibility = View.VISIBLE

            bindTicketCancellationData()

            binding.btnCancelBooking.setOnClickListener {
                setUpAlertDialog()
            }

        }

    }

    private fun setUpAlertDialog() {
        val dialog = MaterialAlertDialogBuilder(this, R.style.CustomAlertDialog)
            .setTitle("Ticket Cancellation!")
            .setMessage("Are you sure delete booking ticket ?")
            .setNegativeButton("No"){ dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("Yes"){dialog, which ->
                mTheMovieBookingModel.deleteTicket(mTicket?.id ?: 0)
                Toast.makeText(this, "Ticket's Canceled", Toast.LENGTH_LONG).show()
                onBackPressed()
            }
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun bindTicketCancellationData() {

        binding.tvMovieName.text = mTicket?.movieName
        binding.tvCinemaName.text = mTicket?.cinemaName

        binding.tvDate.text = mTicket?.ticketCheckout?.booking_date
        binding.tvTime.text = mTicket?.ticketCheckout?.timeslot?.startTime
        binding.tvPlace.text = mTicket?.address

        binding.tvTicketCount.text = mTicket?.ticketCheckout?.total_seat.toString()
        binding.tvSeat.text = mTicket?.ticketCheckout?.seat

        val ticketTotalPrice = mTicket?.ticketCheckout?.total_seat?.times(2) ?: 0
        binding.tvSeatTotalPrice.text = "${ticketTotalPrice}Ks"

        mTicket?.snackList?.let { mAdapter.setNewData(it) }
        val snackTotalPrice = getSnackTotalPriceForTicketCancellation()
        binding.tvSnackTotalPrice.text = snackTotalPrice

        val totalPrice = ticketTotalPrice + getSnackTotalPriceForTicketCancellation().toInt() + 500
        binding.tvTotalPrice.text = totalPrice.toString()

        val refundPrice = totalPrice / 2
        binding.tvRefundPrice.text = "${refundPrice}Ks"

    }

    private fun getSnackTotalPriceForTicketCancellation(): String {
        var snackTotalPrice = 0
        mTicket?.snackList?.forEach {
            snackTotalPrice += it.price?.times(it.quantity) ?: 0
        }
        return snackTotalPrice.toString()
    }


    @SuppressLint("SetTextI18n")
    private fun bindCheckoutTicketData() {

        binding.tvMovieName.text = mMovieName
        binding.tvCinemaName.text = mCinemaName
        binding.tvTime.text = mCinemaTime
        binding.tvDate.text = changeBookingDate()
        binding.tvPlace.text = mCinemaAddress
        binding.tvTicketCount.text = mNumberOfTicket

        val totalSeatPrice = mTotalTicketPrice
        binding.tvSeatTotalPrice.text = "${totalSeatPrice}Ks"

        binding.tvSeat.text = getTicketList()

        val totalSnackPrice = mTotalSnackPrice
        binding.tvSnackTotalPrice.text = "${totalSnackPrice}Ks"

        mAdapter.setNewData(setUpSnackList())

        val totalPrice = mTotalTicketPrice!! + mTotalSnackPrice!! + 500
        binding.tvTotalPrice.text = "${totalPrice}Ks"
    }

    private fun setUpSnackList(): List<SnackVO> {
        for (snack in mTicketInfo?.snackList!!){
            if (snack.quantity > 0){
                mSnackList.add(snack)
            }
        }
        return mSnackList
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

    private fun setUpListeners() {
        binding.btnBackCheckoutScreen.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.llSnackList.setOnClickListener {
            mPresenter.onTapSnackDownButton()
        }

        binding.btnTicketCancel.setOnClickListener {
            mPresenter.onTapTicketPolicyButton()
        }

        binding.btnContinue.setOnClickListener {
            mPresenter.onTapContinueButton()
        }

        binding.btnCancelBooking.setOnClickListener {
            mPresenter.onTapCancelBookingButton()
        }
    }

    private fun setUpRecyclerView() {
        mAdapter = SnackTicketAdapter(mPresenter)
        binding.rvSnackList.adapter = mAdapter
        binding.rvSnackList.layoutManager =
            LinearLayoutManager(this)
    }

    override fun showSnackList() {
        if (isClickedSnackList){
            binding.btnDownArrow.setImageResource(R.drawable.down_arrow)
            binding.rvSnackList.visibility = View.VISIBLE
            isClickedSnackList = false
        }else{
            binding.btnDownArrow.setImageResource(R.drawable.up_arrow)
            binding.rvSnackList.visibility = View.GONE
            isClickedSnackList = true
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showDeleteSnack(snackId: Int) {
        for (snack in mSnackList){
            if (snack.id == snackId) {
                mSnackList.remove(snack)
                mTotalSnackPrice = mTotalSnackPrice!! - (snack.price?.times(snack.quantity)!!)
                binding.tvSnackTotalPrice.text = mTotalSnackPrice.toString()
            }

            val totalPrice = (mTotalTicketPrice?.plus(mTotalSnackPrice!!)!! + 500).toString()
            binding.tvTotalPrice.text = "${totalPrice}Ks"
            Toast.makeText(this, "${snack.name} is removed", Toast.LENGTH_LONG).show()
            break
        }
        mAdapter.setNewData(mSnackList)
    }

    override fun showDialogTicketCancellationPolicy() {
        val dialog = CancellationPolicyFragment()
        dialog.show(supportFragmentManager, "Ticket Policy")
    }

    override fun navigateToBackScreen() {
        onBackPressed()
    }

    override fun navigateToPaymentScreen() {
        mFinalTicketInfo = TicketData(mMovieId, mMovieName, mTicketInfo?.cinemaInfo, mTicketInfo?.seatInfo, mTotalSnackPrice.toString(), mSnackList)
        startActivity(PaymentActivity.newIntent(this, mFinalTicketInfo, mSnackInfo))
    }

    override fun navigateToPreviousScreen() {
        onBackPressed()
    }

}


