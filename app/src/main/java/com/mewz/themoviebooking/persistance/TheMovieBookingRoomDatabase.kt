package com.mewz.themoviebooking.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mewz.themoviebooking.data.vos.cinema.CinemaInfoVO
import com.mewz.themoviebooking.data.vos.cinema.ConfigVO
import com.mewz.themoviebooking.data.vos.login.CitiesVO
import com.mewz.themoviebooking.data.vos.movie.BannerVO
import com.mewz.themoviebooking.data.vos.movie.MovieVO
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.network.responses.OtpResponse
import com.mewz.themoviebooking.persistance.daos.TheMovieBookingDao


@Database(entities = [CitiesVO::class, OtpResponse::class,
BannerVO::class, MovieVO::class, ConfigVO::class, CinemaInfoVO::class,
                     TicketInformation::class], version = 2, exportSchema = false)
abstract class TheMovieBookingRoomDatabase: RoomDatabase() {

    abstract fun getDao(): TheMovieBookingDao

    companion object {
        const val DB_NAME = "THE_MOVIE_BOOKING_DB"
        var dbInstance: TheMovieBookingRoomDatabase? = null

        fun getDBInstance(context: Context): TheMovieBookingRoomDatabase? {
            when (dbInstance){
                null -> {
                    dbInstance = Room.databaseBuilder(context, TheMovieBookingRoomDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return dbInstance
        }
    }

}