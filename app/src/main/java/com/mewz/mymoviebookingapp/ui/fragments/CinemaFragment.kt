package com.mewz.mymoviebookingapp.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.databinding.FragmentCinemaBinding
import com.mewz.mymoviebookingapp.ui.activities.cinema.CinemaDetailActivity
import com.mewz.mymoviebookingapp.ui.adapters.movie.MovieCinemaAdapter
import com.mewz.mymoviebookingapp.ui.delgates.CinemaDetailViewHolderDelegate
import java.time.LocalDate
import java.util.Locale

class CinemaFragment(): Fragment(), CinemaDetailViewHolderDelegate {

    private lateinit var binding: FragmentCinemaBinding
    private lateinit var mMovieCinemaAdapter: MovieCinemaAdapter
    private var isClickedFilter: Boolean = false

    private var mCinemaId: Int? = null

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_cinema, container, false)

        binding = FragmentCinemaBinding.inflate(inflater,container,false)
        var spinFacilities = binding.spFacilities
        val facilitiesAdapter = ArrayAdapter(requireContext(),
            R.layout.select_spinner,
            resources.getStringArray(R.array.facilities))

        spinFacilities.setAdapter(facilitiesAdapter)


        var spinFormat = binding.spFormat
        val formatAdapter = ArrayAdapter(requireContext(),
            R.layout.select_spinner,
            resources.getStringArray(R.array.format))

        spinFormat.setAdapter(formatAdapter)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        setUpCinemaRecyclerView()
        requestCinemaData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestCinemaData() {
        val date = LocalDate.now().toString()
        mMovieBookingModel.getCinemaAndTimeslot(
            authorization = "Bearer ${mMovieBookingModel.getToken(201)?.token}",
            date = date,
            onSuccess = {
                mMovieCinemaAdapter.setNewData(it)
            },
            onFailure = {
                showErrorMessage(it)
            }
        )
    }

    private fun setUpListeners() {
        binding.btnCinemaFilter.setOnClickListener{
            if (isClickedFilter){
                binding.btnCinemaFilter.setColorFilter(ContextCompat.getColor(requireContext(),R.color.white),android.graphics.PorterDuff.Mode.MULTIPLY)
                binding.rlFilter.visibility = View.GONE
                isClickedFilter = false
            }else{
                binding.btnCinemaFilter.setColorFilter(ContextCompat.getColor(requireContext(),R.color.green),android.graphics.PorterDuff.Mode.MULTIPLY)
                binding.rlFilter.visibility = View.VISIBLE
                isClickedFilter = true
            }
        }

    }

    private fun setUpCinemaRecyclerView() {
        mMovieCinemaAdapter = MovieCinemaAdapter(this)
        binding.rvCinemaList.adapter = mMovieCinemaAdapter
        binding.rvCinemaList.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
    }

    override fun onTapSeeDetail(cinemaId: Int) {
        mCinemaId = cinemaId
        startActivity(this.context?.let { CinemaDetailActivity.newIntent(it, mCinemaId!!) })
    }

    override fun onTapDateCard(cinemaDate: String) {
    }

    override fun onTapTimeslot(cinemaTimeslotId: Int, cinemaTime: String) {
    }

    override fun getCinemaName(cinemaName: String?) {
    }

    override fun getCinemaId(cinemaId: Int?) {
    }

    override fun onClickCinema(cinemaId: Int?) {
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(requireContext(),error, Toast.LENGTH_LONG).show()
    }


}