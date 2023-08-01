package com.mewz.mymoviebookingapp.ui

import android.app.Application
import com.mewz.mymoviebookingapp.data.models.MovieBookingModelImpl

class MovieBookingApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MovieBookingModelImpl.initMovieBookingDatabase(applicationContext)
    }
}