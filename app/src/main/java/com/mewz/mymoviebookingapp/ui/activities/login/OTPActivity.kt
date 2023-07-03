package com.mewz.mymoviebookingapp.ui.activities.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.databinding.ActivityOtpBinding

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object{
        private const val EXTRA_PHONE = "EXTRA_PHONE"
        private const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        fun newIntent(context: Context, phone: String ,message: String): Intent{
            val intent = Intent(context,OTPActivity::class.java)
            intent.putExtra(EXTRA_PHONE,phone)
            intent.putExtra(EXTRA_MESSAGE,message)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        Toast.makeText(this,"$message",Toast.LENGTH_LONG).show()

        val phone = intent?.getStringExtra(EXTRA_PHONE).toString()

        setUpConfirmOTPButton(phone)
    }

    private fun setUpConfirmOTPButton(phone: String) {
        binding.btnConfirm.setOnClickListener {

            val otp = binding.otpView.otp
            mMovieBookingModel.checkOTP(
                phone = phone,
                otp = otp.toString(),
                onSuccess = {
                    if (otp == "123456"){
                        startActivity(RegionActivity.newIntent(this))
                    }else{
                        val msg = it.message.toString()
                        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
                    }
                },
                onFailure = {
                    showErrorMessage(it)
                }
            )
        }
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
    }


}