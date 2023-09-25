package com.mewz.themoviebooking.activities.movies

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.adapters.PaymentAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutBody
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutSnackList
import com.mewz.themoviebooking.databinding.ActivityPaymentBinding
import com.mewz.themoviebooking.mvp.presenters.PaymentPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.PaymentPresenterImpl
import com.mewz.themoviebooking.mvp.views.PaymentView
import com.mewz.themoviebooking.utils.data.TicketData

@RequiresApi(Build.VERSION_CODES.O)
class PaymentActivity : BaseActivity(), PaymentView {

    private lateinit var binding: ActivityPaymentBinding

    private lateinit var mAdapter: PaymentAdapter

    private lateinit var mPresenter: PaymentPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var mTicketInfo: TicketData? = null
    private var mSnackInfo: CheckoutSnackList? = null
    private var mMovieId: Int? = 0
    private var mCinemaDate: String? = null
    private var mCinemaTimeslotId: Int? = 0
    private var mTicketList: MutableList<String> = mutableListOf()

    companion object{

        const val EXTRA_TICKET_INFO = "EXTRA_TICKET_INFO"
        const val EXTRA_SNACK_INFO = "EXTRA_SNACK_INFO"

        fun newIntent(context: Context, ticketInfo: TicketData?, snackInfo: CheckoutSnackList?): Intent {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra(EXTRA_TICKET_INFO, ticketInfo)
            intent.putExtra(EXTRA_SNACK_INFO, snackInfo)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTicketInfo = intent?.getSerializableExtra(EXTRA_TICKET_INFO) as TicketData
        mSnackInfo = intent?.getSerializableExtra(CheckoutActivity.EXTRA_SNACK_INFO) as CheckoutSnackList
        Log.d("PaymentData","data.$mTicketInfo data.$mSnackInfo")


        mMovieId = mTicketInfo?.movieId?.toInt()
        mCinemaDate = mTicketInfo?.cinemaInfo?.date
        mCinemaTimeslotId = mTicketInfo?.cinemaInfo?.cinemaTimeslotId
        mTicketList = mTicketInfo?.seatInfo?.ticketList ?: mutableListOf()

        setUpPresenter()
        setUpRecyclerView()
        setUpListener()

        setUpRequestData()
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<PaymentPresenterImpl, PaymentView>()
    }

    private fun setUpRequestData() {
        mTheMovieBookingModel.getPaymentType(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            onSuccess = { mAdapter.setNewData(it) },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
    }

    private fun setUpListener() {
        binding.btnBackPaymentScreen.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.btnOffer.setOnClickListener {
            mPresenter.onTapOfferButton()
        }
    }

    private fun setUpRecyclerView() {
        mAdapter = PaymentAdapter(mPresenter)
        binding.rvPaymentList.adapter = mAdapter
        binding.rvPaymentList.layoutManager =
            LinearLayoutManager(this)
    }


    override fun navigateToBackScreen() {
        onBackPressed()
    }

    override fun navigateToTicketInformationScreen(paymentId: Int) {
        mTheMovieBookingModel.getCheckoutTicket(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            checkoutTicket = getCheckoutTicket(paymentId),
            onSuccess = {
                startActivity(TicketInformationActivity.newIntent(this, paymentId, mTicketInfo, it)) },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
    }

    private fun getCheckoutTicket(paymentId: Int): CheckoutBody {
        val snackList = mSnackInfo?.snackList
        return CheckoutBody(
            mCinemaTimeslotId,
            getTicketList(),
            mCinemaDate,
            mMovieId,
            paymentId,
            snackList
        )
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
}