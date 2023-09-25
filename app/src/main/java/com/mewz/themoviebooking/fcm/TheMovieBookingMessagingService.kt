package com.mewz.themoviebooking.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class TheMovieBookingMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM Token", token);
    }

}