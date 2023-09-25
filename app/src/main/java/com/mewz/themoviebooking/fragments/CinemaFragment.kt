package com.mewz.themoviebooking.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.ViewModelProvider
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.MainActivity
import com.mewz.themoviebooking.activities.cinema.CinemaInfoActivity
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.databinding.FragmentCinemaBinding
import com.mewz.themoviebooking.mvp.presenters.CinemaPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.CinemaPresenterImpl
import com.mewz.themoviebooking.mvp.views.CinemaView
import com.mewz.themoviebooking.views.viewpods.CinemaViewPod
import java.time.LocalDate

class CinemaFragment : Fragment(), CinemaView {

    private lateinit var binding: FragmentCinemaBinding

    private lateinit var mViewPod: CinemaViewPod

    private lateinit var mPresenter: CinemaPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var isClickedFilter: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_cinema, container, false)

        binding = FragmentCinemaBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()

        setUpSpinner()
        setUpViewPod()
        setUpListeners()

        setUpRequestData()

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(requireActivity())[CinemaPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpRequestData() {
        mTheMovieBookingModel.getCinemaAndShowtimeByDate(
            authorization = "Bearer ${mTheMovieBookingModel.getToken()?.token}",
            date = LocalDate.now().toString(),
            onSuccess = { mViewPod.setData(it) },
            onFailure = { Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show() }
        )
    }

    private fun setUpListeners() {
        binding.btnBackCinemaScreen.setOnClickListener {
            mPresenter.onTapBackScreen()
        }

        binding.btnFilter.setOnClickListener {
            if (isClickedFilter){
                binding.btnFilter.setColorFilter(requireActivity().resources.getColor(R.color.white))
                binding.rlFilterView.visibility = View.GONE
                isClickedFilter = false
            }else{
                binding.btnFilter.setColorFilter(requireActivity().resources.getColor(R.color.colorAccent))
                binding.rlFilterView.visibility = View.VISIBLE
                isClickedFilter = true
            }
        }
    }

    private fun setUpViewPod() {
        mViewPod = binding.vpCinemaList.root
        mViewPod.setUpCinemaViewPod(mPresenter)
    }

    private fun setUpSpinner() {
        val spinnerFacilities = view?.findViewById<AppCompatSpinner>(R.id.spinnerFacilities)
        ArrayAdapter.createFromResource(requireContext(),
            R.array.spinner_facilities, android.R.layout.simple_spinner_dropdown_item).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFacilities?.adapter = it
        }

        val spinnerFormat = view?.findViewById<AppCompatSpinner>(R.id.spinnerFormat)
        ArrayAdapter.createFromResource(requireContext(),
            R.array.spinner_format, android.R.layout.simple_spinner_dropdown_item).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFormat?.adapter = it
        }
    }

    override fun showCinemaId(cinemaId: Int?) {
        // Toast.makeText(requireActivity(), cinemaId.toString(), Toast.LENGTH_LONG).show()
    }

    override fun showCinemaName(cinemaName: String?) {
        // Toast.makeText(requireActivity(), cinemaName, Toast.LENGTH_LONG).show()
    }

    override fun navigateToMainScreen() {
        startActivity(MainActivity.newIntent(requireActivity(), ""))
    }

    override fun navigateToSeatScreen(timeslotId: Int?, cinemaTime: String?) {
        // startActivity(ChooseSeatActivity.newIntent(requireActivity(), "", "", ))
    }

    override fun navigateToCinemaDetailScreen(cinemaId: Int?) {
        startActivity(cinemaId?.let { CinemaInfoActivity.newIntent(requireActivity(), it) })
        Toast.makeText(requireActivity(), cinemaId.toString(), Toast.LENGTH_LONG).show()
    }

    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show()
    }


}