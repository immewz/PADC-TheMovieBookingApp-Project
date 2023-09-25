package com.mewz.themoviebooking.activities.login

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.databinding.ActivityOtpBinding
import com.mewz.themoviebooking.mvp.presenters.OtpPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.OtpPresenterImpl

class OtpActivity : BaseActivity(), com.mewz.themoviebooking.mvp.views.OtpView{

    private lateinit var binding: ActivityOtpBinding

    private lateinit var mPresenter: OtpPresenter

    private var mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    private var mPhone: String? = null

    companion object{

        const val EXTRA_PHONE = "EXTRA_PHONE"
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"

        fun newIntent(context: Context, phone: String, message: String): Intent{
            val intent = Intent(context, OtpActivity::class.java)
            intent.putExtra(EXTRA_PHONE, phone)
            intent.putExtra(EXTRA_MESSAGE, message)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(binding.root)
        }else{
            setContentView(binding.root)
        }

        mPhone = intent?.getStringExtra(EXTRA_PHONE) ?: ""
        val message = intent?.getStringExtra(EXTRA_MESSAGE) ?: ""

        Toast.makeText(this, "$mPhone + $message", Toast.LENGTH_LONG).show()

        setUpPresenter()
        setUpListeners()


    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<OtpPresenterImpl, com.mewz.themoviebooking.mvp.views.OtpView>()
    }


    private fun setUpListeners() {
        binding.btnBackOtpScreen.setOnClickListener {
            mPresenter.onTapBack()
        }

        binding.btnConfirmOtp.setOnClickListener {
            val otpCode = binding.otpView.text.toString()

            if (otpCode == "123456"){
                mTheMovieBookingModel.signInWithPhone(
                    phone = mPhone.toString(),
                    otp = otpCode,
                    onSuccess = { mPresenter.onTapConfirmOTP(otpCode, it.token.toString()) },
                    onFailure = { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
                )
            }else{
                Toast.makeText(this, "Wrong OTP", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun navigateToLocationScreen(otpCode: String, token: String) {
        startActivity(LocationActivity.newIntent(this, otpCode, token ))
        finish()
    }

    override fun navigateToLoginScreen() {
        onBackPressed()
    }
}