package com.mewz.themoviebooking

import android.app.Application
import com.mewz.themoviebooking.data.models.TheMovieBookingModelImpl

class TheMovieBookingApp: Application() {
    override fun onCreate() {
        super.onCreate()
        TheMovieBookingModelImpl.initTheMovieBookingDatabase(applicationContext)
    }


}