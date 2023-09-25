package com.mewz.themoviebooking.activities.login

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.messaging.FirebaseMessaging
import com.hbb20.CountryCodePicker
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.BaseActivity
import com.mewz.themoviebooking.data.models.TheMovieBookingModel
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl
import com.mewz.themoviebooking.databinding.ActivityLoginBinding
import com.mewz.themoviebooking.mvp.presenters.LoginPresenter
import com.mewz.themoviebooking.mvp.presenters.impls.LoginPresenterImpl
import com.mewz.themoviebooking.mvp.views.LoginView


class LoginActivity : BaseActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var mPresenter: LoginPresenter

    private val mTheMovieBookingModel: TheMovieBookingModel = TheMovieBookingModelImpl

    companion object{
        fun newIntent(context: Context): Intent{
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(binding.root)
        }else{
            setContentView(binding.root)
        }


        setUpPresenter()

        setUpListeners()

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM Token", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            // Get the FCM token
            val token = task.result
            Log.d("FCM Token", token ?: "Token is null")
            // You can send this token to your server or use it for notifications.
        }

        mPresenter.onUiReady(this, this)

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<LoginPresenterImpl, LoginView>()
    }

    private fun setUpListeners() {

        binding.btnVerifyPhone.setOnClickListener {

            val ccp = findViewById<CountryCodePicker>(R.id.countryCodePicker)
            val phone = findViewById<AppCompatEditText>(R.id.edtPhoneNumber)
            ccp.registerCarrierNumberEditText(phone)
            val fullNumber = ccp.fullNumber

            mTheMovieBookingModel.getOtp(
                phone = fullNumber,
                onSuccess = {mPresenter.onTapVerifyPhoneNumber(fullNumber, it.message.toString())},
                onFailure = {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
            )

        }

    }

    override fun displayLoginSentence(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_LONG).show()
        binding.tvVerifyPhone.text = title
    }

    override fun navigateToOTPScreen(phone: String, message: String) {
        startActivity(OtpActivity.newIntent(this, phone, message))
    }



}