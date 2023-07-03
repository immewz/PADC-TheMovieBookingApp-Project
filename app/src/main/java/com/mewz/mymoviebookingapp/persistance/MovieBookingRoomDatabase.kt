package com.mewz.mymoviebookingapp.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mewz.mymoviebookingapp.data.vos.cinema.CinemaInfoVO
import com.mewz.mymoviebookingapp.data.vos.login.CitiesVO
import com.mewz.mymoviebookingapp.data.vos.movie.BannerVO
import com.mewz.mymoviebookingapp.data.vos.movie.MovieVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaConfigVO
import com.mewz.mymoviebookingapp.data.vos.ticket.TicketInformation
import com.mewz.mymoviebookingapp.network.responses.login.OtpResponse
import com.mewz.mymoviebookingapp.persistance.dao.MovieBookingDao

@Database(entities = [CitiesVO::class,OtpResponse::class,BannerVO::class,
MovieVO::class,CinemaConfigVO::class,CinemaInfoVO::class,TicketInformation::class],
version = 1, exportSchema = false)
abstract class MovieBookingRoomDatabase: RoomDatabase() {

    abstract fun getDao(): MovieBookingDao

    companion object {
        private var roomDB: MovieBookingRoomDatabase? = null
        private const val dbName = "MovieBookingDB"

        fun getDBInstance(context: Context): MovieBookingRoomDatabase? {
            when(roomDB){
                null -> {
                    roomDB = Room.databaseBuilder(context,MovieBookingRoomDatabase::class.java, dbName)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return roomDB
        }
    }
}