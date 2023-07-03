package com.mewz.mymoviebookingapp.ui.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.mewz.mymoviebookingapp.data.models.MovieBookingModel
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl
import com.mewz.mymoviebookingapp.databinding.ActivitySplashBinding
import com.mewz.mymoviebookingapp.ui.activities.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    // Model
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    private val SPLASH_TIME: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpLoginOrMain()
        setUpNetworkCall()
    }

    private fun setUpLoginOrMain() {
        Handler().postDelayed({
            if (mMovieBookingModel.getToken(201)?.token?.isNotEmpty() == true){
                startActivity(MainActivity.newIntent(this,null.toString()))
            }else{
                startActivity(LoginActivity.newIntent(this))
                finish()
            }

        },SPLASH_TIME)
    }

    private fun setUpNetworkCall() {
        mMovieBookingModel.insertCities(
            onSuccess = {
                Toast.makeText(this,"Cities succeeded ", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(this,"Cities failed", Toast.LENGTH_SHORT).show()
            }
        )

        mMovieBookingModel.insertCinemaConfig(
            onSuccess = {
                Toast.makeText(this,"Config succeeded ", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(this,"Config failed", Toast.LENGTH_SHORT).show()
            }
        )

        mMovieBookingModel.insertCinemaInfo(
            onSuccess = {
                Toast.makeText(this,"CinemaInfo succeeded ", Toast.LENGTH_SHORT).show()
            },
            onFailure = {
                Toast.makeText(this,"CinemaInfo failed", Toast.LENGTH_SHORT).show()
            }
        )
    }


}