package com.mewz.themoviebooking.activities.login

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.MainActivity
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.mvp.presenters.LogoPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.LogoPresenterImpl
import com.mewz.themoviebooking.mvp.views.LogoView

class LogoActivity : BaseActivity(), LogoView{

    private lateinit var mPresenter: LogoPresenter

    private val mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private val SPLAH_TIME = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        setUpPresenter()

        Handler().postDelayed({
            if (mTheMovieBookingModel.getToken()?.token?.isNotEmpty() == true) {
                startActivity(MainActivity.newIntent(this, "Yangon"))
            }else{
                startActivity(LoginActivity.newIntent(this))
                finish()
            }
        }, SPLAH_TIME)

        setUpRequestData()

        mPresenter.onUiReady(this, this)

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<LogoPresenterImpl, LogoView>()
    }

    private fun setUpRequestData() {
        mTheMovieBookingModel.insertCities(
            onSuccess = { Toast.makeText(this, "Success Call Cities", Toast.LENGTH_LONG).show() },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )

         mTheMovieBookingModel.insertConfig(
            onSuccess = { Toast.makeText(this, "Success Call Config", Toast.LENGTH_LONG).show() },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )

        mTheMovieBookingModel.insertCinema(
            onSuccess = { Toast.makeText(this, "Success Call CinemaInfo", Toast.LENGTH_LONG).show() },
            onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        )
    }

}