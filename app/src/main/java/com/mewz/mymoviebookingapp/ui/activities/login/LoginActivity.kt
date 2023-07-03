package com.mewz.mymoviebookingapp.ui.activities.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.hbb20.CountryCodePicker
import com.mewz.mymoviebookingapp.R
import com.mewz.mymoviebookingapp.ui.checkers.ValidationPhoneNumber
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.databinding.ActivityLoginBinding
import com.mewz.mymoviebookingapp.ui.fragments.ProfileFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mValidationPhoneNumber: ValidationPhoneNumber

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpVerifyPhoneNoButton()
        Toast.makeText(this,"",Toast.LENGTH_LONG).show()

    }

    private fun setUpVerifyPhoneNoButton() {
        binding.btnVerifyPhoneNo.setOnClickListener {

            val edtPhone = findViewById<AppCompatEditText>(R.id.etMobilePhone)
            val ccp = findViewById<CountryCodePicker>(R.id.countryCodePicker)

            val number = edtPhone.text.toString()
            mValidationPhoneNumber = ValidationPhoneNumber(number)

            ccp.registerCarrierNumberEditText(edtPhone)
            val phone = ccp.fullNumber

            if(isValidatedPhoneNumber()){
                edtPhone.error = null
                mMovieBookingModel.getOTP(
                    phone = phone,
                    onSuccess = {otp ->
                        val msg = otp.message.toString()
                                startActivity(OTPActivity.newIntent(this,phone,msg))
//                        Log.d("Login","dataLogin.$it")
                    },
                    onFailure = {
                        showErrorMessage(it)
                    }
                )
            }


        }
    }

    private fun showErrorMessage(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show()
    }

    private fun isValidatedPhoneNumber(): Boolean {
        return mValidationPhoneNumber.isValidatedPhoneNumber()
    }
}