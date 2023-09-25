package com.mewz.themoviebooking.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.mewz.themoviebooking.R
import com.mewz.themoviebooking.activities.login.LocationActivity
import com.mewz.themoviebooking.databinding.ActivityMainBinding
import com.mewz.themoviebooking.fragments.CinemaFragment
import com.mewz.themoviebooking.fragments.MovieFragment
import com.mewz.themoviebooking.fragments.ProfileFragment
import com.mewz.themoviebooking.fragments.TicketFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object{

        const val EXTRA_CITY = "EXTRA_CITY"

        fun newIntent(context: Context, city: String): Intent{
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_CITY, city)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(binding.root)
        }else{
            setContentView(binding.root)
        }

        val city = intent?.getStringExtra(EXTRA_CITY) ?: ""
        // Toast.makeText(this, city, Toast.LENGTH_LONG).show()

        setUpBottomNavigation()

    }

    private fun setUpBottomNavigation() {
        switchFragment(MovieFragment())
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.movieFragment -> {
                    switchFragment(MovieFragment())
                    true
                }
                R.id.cinemaFragment -> {
                    switchFragment(CinemaFragment())
                    true
                }
                R.id.ticketFragment -> {
                    switchFragment(TicketFragment())
                    true
                }
                R.id.profileFragment -> {
                    switchFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }


    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}