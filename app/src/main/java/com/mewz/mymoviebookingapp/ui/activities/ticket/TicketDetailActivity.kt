package com.mewz.mymoviebookingapp.ui.activities.ticket

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.data.vos.ticket.TicketInformation
import com.mewz.mymoviebookingapp.databinding.ActivityTicketDetailBinding
import kotlin.math.log

class TicketDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTicketDetailBinding
    private var mTicketInformation: List<TicketInformation>? = null
    private var mTicketId: Int? = null

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object{
        private const val EXTRA_TICKET_ID = "EXTRA_TICKET_ID"
        fun newIntent(context: Context, ticketId: Int): Intent {
            val intent = Intent(context,TicketDetailActivity::class.java)
            intent.putExtra(EXTRA_TICKET_ID, ticketId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTicketId = intent?.getIntExtra(EXTRA_TICKET_ID, 0)
        Toast.makeText(this,"$mTicketId",Toast.LENGTH_LONG).show()

        //bindData(mTicketId)
        setUpListeners()

        mTicketInformation = mMovieBookingModel.getAllTicket() ?: listOf()
        Log.d("TicketActivity","$mTicketInformation")
    }

    private fun bindData(mTicketId: Int?) {
        for (ticketList in mTicketInformation!!){
            if (ticketList.id == mTicketId){
                binding.tvMovieTitle.text = ticketList.movieName
            }
        }
//        binding.tvMovieTitle.text = mTicketInformation?.movieName ?: ""
//        binding.tvCinemaDate.text = mTicketInformation?.ticketCheckout?.bookingDate ?: ""
//        binding.tvCinemaTime.text = mTicketInformation?.ticketCheckout?.timeslot?.startTime ?: ""
//        binding.tvCinemaLocation.text = mTicketInformation?.address
//        binding.tvTicketCount.text = mTicketInformation?.ticketCheckout?.totalSeat.toString()
//        binding.tvReceiptSeat.text = mTicketInformation?.ticketCheckout?.seat ?: ""


    }

    private fun setUpListeners() {
        binding.btnCancel.setOnClickListener {
            ticketCancelDialog()
        }
    }

    private fun ticketCancelDialog() {
        val dialog = MaterialAlertDialogBuilder(this, R.style.RoundedAlertDialog)
            .setTitle("Ticket Cancellation !")
            .setMessage("Are you sure to cancel ?")
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog, which ->
                Toast.makeText(this, "Ticket's cancelled", Toast.LENGTH_SHORT).show()
                mTicketId?.let { mMovieBookingModel.deleteTicket(it) }
                super.onBackPressed()
            }
            .setNegativeButton("Cancel") { dialog, which -> dialog?.dismiss() }
            .create()
        dialog.show()
    }


}