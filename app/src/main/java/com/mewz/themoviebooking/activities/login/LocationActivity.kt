package com.mewz.themoviebooking.activities.login

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.activities.MainActivity
import com.mewz.themoviebooking.adapters.CitiesAdapter
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.databinding.ActivityLocationBinding
import com.mewz.themoviebooking.delegates.CitiesViewHolderDelegate
import com.mewz.themoviebooking.mvp.presenters.LocationPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.LocationPresenterImpl
import com.mewz.themoviebooking.mvp.views.LocationView

class LocationActivity : BaseActivity(), LocationView {

    private lateinit var binding: ActivityLocationBinding

    private lateinit var mAdapter: CitiesAdapter

    private lateinit var mPresenter: LocationPresenter

    private val mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    companion object{

        const val EXTRA_OTP = "EXTRA_OTP"
        const val EXTRA_TOKEN = "EXTRA_TOKEN"

        fun newIntent(context: Context, otp: String, token: String): Intent{
            val intent = Intent(context, LocationActivity::class.java)
            intent.putExtra(EXTRA_OTP, otp)
            intent.putExtra(EXTRA_TOKEN, token)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(binding.root)
        }else{
            setContentView(binding.root)
        }

        val otp = intent?.getStringExtra(EXTRA_OTP) ?: ""
        val token = intent?.getStringExtra(EXTRA_TOKEN) ?: ""
        // Toast.makeText(this, token, Toast.LENGTH_LONG).show()

        setUpPresenter()
        setUpRecyclerView()

        setUpRequestData()

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<LocationPresenterImpl, LocationView>()
    }

    private fun setUpRequestData() {
        mTheMovieBookingModel.getCities()?.let {
            mAdapter.setNewData(it)
        }
    }

    private fun setUpRecyclerView() {
        mAdapter = CitiesAdapter(mPresenter)
        binding.rvCitiesList.adapter = mAdapter
        binding.rvCitiesList.layoutManager = LinearLayoutManager(this)
    }

    override fun navigateToMainScreen(city: String) {
        startActivity(MainActivity.newIntent(this, city))
        finish()
        Toast.makeText(this, city, Toast.LENGTH_LONG).show()
    }
}