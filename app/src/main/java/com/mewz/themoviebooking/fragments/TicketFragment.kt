package com.mewz.themoviebooking.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mewz.themoviebooking.activities.movies.CheckoutActivity
import com.mewz.themoviebooking.activities.movies.PaymentActivity
import com.mewz.themoviebooking.adapters.TicketAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.data.vos.confirmation.CheckoutSnackList
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.databinding.FragmentTicketBinding
import com.mewz.themoviebooking.delegates.TicketViewHolderDelegate
import com.mewz.themoviebooking.mvp.presenters.TicketPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.TicketPresenterImpl
import com.mewz.themoviebooking.mvp.views.TicketView
import com.mewz.themoviebooking.utils.data.CinemaData
import com.mewz.themoviebooking.utils.data.SeatData
import com.mewz.themoviebooking.utils.data.TicketData

@RequiresApi(Build.VERSION_CODES.O)
class TicketFragment : Fragment(), TicketView {

    private lateinit var binding: FragmentTicketBinding

    private lateinit var mAdapter: TicketAdapter

    private lateinit var mPresenter: TicketPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var mMovieId: String? = null
    private var mMovieName: String? = null
    private var mCinemaInfo: CinemaData? = null
    private var mSeatInfo: SeatData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_ticket, container, false)

        binding = FragmentTicketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpRecyclerView()
        setUpRequestData()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(requireActivity())[TicketPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpRequestData() {
        mAdapter.setNewData(mTheMovieBookingModel.getAllTickets() ?: listOf())
    }

    private fun setUpRecyclerView() {
        mAdapter = TicketAdapter(mPresenter, "TicketDetail")
        binding.rvTicketList.adapter = mAdapter
        binding.rvTicketList.layoutManager = LinearLayoutManager(requireActivity())

    }

    override fun navigateToTicketCancel(ticketData: TicketInformation?) {
        val ticketInfo = TicketData(null, null, null, null, null, null)
        val snackInfo = CheckoutSnackList(null)
        startActivity(CheckoutActivity.newIntent(requireActivity(), "TicketDetail", ticketInfo, snackInfo, ticketData))
        // Toast.makeText(requireActivity(), "$ticketData", Toast.LENGTH_LONG).show()
    }


    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show()
    }


}