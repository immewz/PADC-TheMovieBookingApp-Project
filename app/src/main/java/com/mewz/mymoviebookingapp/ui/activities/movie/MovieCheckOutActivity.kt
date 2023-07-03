package com.mewz.mymoviebookingapp.ui.activities.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutSnack
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutSnackList
import com.mewz.mymoviebookingapp.data.vos.movie.checkout.CheckoutVO
import com.mewz.mymoviebookingapp.data.vos.movie.snack.SnackVO
import com.mewz.mymoviebookingapp.data.vos.ticket.TicketInformation
import com.mewz.mymoviebookingapp.databinding.ActivityMovieCheckOutBinding
import com.mewz.mymoviebookingapp.ui.adapters.movie.SnackCheckoutAdapter
import com.mewz.mymoviebookingapp.ui.delgates.SnackCheckoutViewHolderDelegate
import com.mewz.mymoviebookingapp.ui.fragments.dialog.TicketCancellationPolicyFragment
import com.mewz.mymoviebookingapp.ui.utils.TicketData

class MovieCheckOutActivity : AppCompatActivity(), SnackCheckoutViewHolderDelegate {

    private lateinit var binding: ActivityMovieCheckOutBinding
    private lateinit var mSnackCheckoutAdapter: SnackCheckoutAdapter

    private var mMovieName: String? = null
    private var mCinemaName: String? = null
    private var mCinemaDate: String? = null
    private var mCinemaTime: String? = null
    private var mCinemaLocation: String? = null
    private var mNumberOfTicket: Int? = null
    private var mTicketTotalPrice: Long? = 0L
    private var mSnackTotalPrice: Long = 0L
    private var mTicketList: MutableList<String> = mutableListOf()
    private lateinit var mCheckoutSnackList: MutableList<SnackVO>

    private var mTicketInfo: TicketData? = null
    private var mSnackInfo: CheckoutSnackList? = null

    companion object{
        private const val EXTRA_TICKET_INFO = "EXTRA_TICKET_INFO"
        private const val EXTRA_SNACK_INFO = "EXTRA_SNACK_INFO"
        fun newIntent(context: Context, ticketInfo: TicketData?, snackInfo: CheckoutSnackList?): Intent{
            val intent = Intent(context,MovieCheckOutActivity::class.java)
            intent.putExtra(EXTRA_TICKET_INFO, ticketInfo)
            intent.putExtra(EXTRA_SNACK_INFO, snackInfo)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTicketInfo = intent?.getSerializableExtra(EXTRA_TICKET_INFO) as TicketData?
        mMovieName = mTicketInfo?.movieInfo?.movieName
        mCinemaName = mTicketInfo?.cinemaInfo?.cinemaName
        mCinemaDate = mTicketInfo?.cinemaInfo?.cinemaData
        mCinemaTime = mTicketInfo?.cinemaInfo?.cinemaTime
        mCinemaLocation = mTicketInfo?.cinemaInfo?.cinemaLocation
        mNumberOfTicket = mTicketInfo?.seatInfo?.numberOfTicket
        mTicketTotalPrice = mTicketInfo?.seatInfo?.ticketTotalPrice
        mSnackTotalPrice = mTicketInfo?.snackPrice ?: 0
        mTicketList = mTicketInfo?.seatInfo?.ticketList ?: mutableListOf()

        mSnackInfo = intent?.getSerializableExtra(EXTRA_SNACK_INFO) as CheckoutSnackList?

        mCheckoutSnackList = mutableListOf()

        setUpOrderFoodListRecyclerView()
        setUpListeners()
    }

    private fun setUpListeners() {

        bindTicketData()

        binding.btnContinue.setOnClickListener {
            startActivity(MoviePaymentActivity.newIntent(this, mTicketInfo, mSnackInfo))
            finish()
        }


        binding.btnPolicy.setOnClickListener {
            val dialog = TicketCancellationPolicyFragment()
            dialog.show(supportFragmentManager,"Ticket Policy")
        }
    }

    private fun bindTicketData() {
        binding.tvMovieTitle.text = mMovieName
        binding.tvCinema.text = mCinemaName
        binding.tvCinemaDate.text = mCinemaDate
        binding.tvCinemaTime.text = mCinemaTime
        binding.tvCinemaLocation.text = mCinemaLocation
        binding.tvTicketCount.text = mNumberOfTicket.toString()
        binding.tvReceiptFoodAmount.text = "$mSnackTotalPrice Ks"

        val ticketTotalPrice = "${mTicketTotalPrice} Ks"
        binding.tvReceiptAmount.text = ticketTotalPrice

        val ticketName = getTicketList()
        binding.tvReceiptSeat.text = ticketName

        val total = "${((mTicketTotalPrice?.plus(mSnackTotalPrice) ?: 0) + 500)} Ks"
        binding.tvTotal.text = total

        mSnackCheckoutAdapter.bindNewData(setUpSnackList(), "Checkout")
    }

    private fun setUpSnackList() : List<SnackVO> {
        for(snack in mTicketInfo?.snackList!!) {
            if(snack.quantity > 0) {
                mCheckoutSnackList.add(snack)
            }
        }
        return mCheckoutSnackList
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

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpOrderFoodListRecyclerView() {
        mSnackCheckoutAdapter = SnackCheckoutAdapter(this)
        binding.rvFoodOrderList.adapter = mSnackCheckoutAdapter
        binding.rvFoodOrderList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        mSnackCheckoutAdapter.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onTapSnack(snackId: Int) {
        for (snack in mCheckoutSnackList) {
            if (snack.id == snackId) {
                mCheckoutSnackList.remove(snack)
                mSnackTotalPrice -= (snack.price?.times(snack.quantity)!!)
                binding.tvReceiptFoodAmount.text = mSnackTotalPrice.toString()

                binding.tvTotal.text =
                    (((mTicketTotalPrice?.plus(mSnackTotalPrice) ?: 0) + 500).toString())

                Toast.makeText(this, "${snack.name} is removed", Toast.LENGTH_SHORT)
                    .show()
                break
            }
        }
        mSnackCheckoutAdapter.bindNewData(mCheckoutSnackList, "Checkout")
    }

}