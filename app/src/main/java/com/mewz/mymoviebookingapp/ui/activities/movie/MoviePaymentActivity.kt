package com.mewz.mymoviebookingapp.ui.activities.movie

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutBody
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutSnackList
import com.mewz.mymoviebookingapp.databinding.ActivityMoviePaymentBinding
import com.mewz.mymoviebookingapp.ui.adapters.movie.MoviePaymentAdapter
import com.mewz.mymoviebookingapp.ui.delgates.PaymentViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.utils.TicketData

class MoviePaymentActivity : AppCompatActivity(), PaymentViewHolderDelegate {

    private lateinit var binding: ActivityMoviePaymentBinding
    private lateinit var mMoviePaymentAdapter: MoviePaymentAdapter

    private var mTicketInfo: TicketData? = null
    private var mSnackInfo: CheckoutSnackList? = null

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object{
        private const val EXTRA_TICKET_INFO = "EXTRA_TICKET_INFO"
        private const val EXTRA_SNACK_INFO = "EXTRA_SNACK_INFO"
        fun newIntent(context: Context, ticketInfo: TicketData?, snackInfo: CheckoutSnackList?): Intent{
            val intent = Intent(context,MoviePaymentActivity::class.java)
            intent.putExtra(EXTRA_TICKET_INFO, ticketInfo)
            intent.putExtra(EXTRA_SNACK_INFO, snackInfo)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTicketInfo = intent?.getSerializableExtra(EXTRA_TICKET_INFO) as TicketData?
        mSnackInfo = intent?.getSerializableExtra(EXTRA_SNACK_INFO) as CheckoutSnackList?

//        Log.d("MoviePayment","${mSnackInfo?.snackList}")

        setUpPaymentRecyclerView()
        requestPaymentData()


    }

    private fun requestPaymentData() {
        mMovieBookingModel.getPaymentType(
            authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
            onSuccess = {
                mMoviePaymentAdapter.setNewData(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )
    }


    private fun setUpPaymentRecyclerView() {
        mMoviePaymentAdapter = MoviePaymentAdapter(this)
        binding.rvPaymentList.adapter = mMoviePaymentAdapter
        binding.rvPaymentList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }


    override fun onTapPayment(payment: Int?) {
        mMovieBookingModel.getCheckoutTicket(
            authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
            checkoutTicket = getCheckoutTicket(payment),
            onSuccess = {
                Log.d("paymentCheckout","$it")
                startActivity(MovieTicketConfirmationActivity.newIntent(this,mTicketInfo,mSnackInfo,payment))
                finish()
            },
            onFailure = {
                showErrorMessage(it)
            }
        )
    }

    private fun getCheckoutTicket(paymentId: Int?): CheckoutBody {
        return CheckoutBody(
            mTicketInfo?.cinemaInfo?.cinemaTimeslotId,
            getTicketList(),
            mTicketInfo?.cinemaInfo?.cinemaData,
            mTicketInfo?.movieInfo?.movieId,
            paymentId,
            mSnackInfo?.snackList ?: mutableListOf()
        )
    }

    private fun getTicketList(): String {
        var seatNumberString = ""
        for (seatNumber in mTicketInfo?.seatInfo?.ticketList ?: mutableListOf()){
            seatNumberString += "$seatNumber,"
        }

        if (seatNumberString.isNotEmpty()){
            seatNumberString = java.lang.StringBuilder(seatNumberString).also {
                it.deleteCharAt(it.lastIndex)
            }.toString()
        }
        return seatNumberString
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this,error, Toast.LENGTH_LONG).show()
    }
}