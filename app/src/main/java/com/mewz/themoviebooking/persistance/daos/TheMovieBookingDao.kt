package com.mewz.themoviebooking.persistance.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mewz.themoviebooking.data.vos.cinema.CinemaInfoVO
import com.mewz.themoviebooking.data.vos.cinema.ConfigVO
import com.mewz.themoviebooking.data.vos.login.CitiesVO
import com.mewz.themoviebooking.data.vos.movie.BannerVO
import com.mewz.themoviebooking.data.vos.movie.MovieVO
import com.mewz.themoviebooking.data.vos.ticket.TicketInformation
import com.mewz.themoviebooking.network.responses.OtpResponse

@Dao
interface TheMovieBookingDao {

    // Location
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: List<CitiesVO>)

    @Query("SELECT * FROM cities_table")
    fun getAllCities(): List<CitiesVO>

    // OTP and get TOKEN
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSignInInformation(otpList: OtpResponse?)

    @Query("SELECT * FROM otp_table")
    fun getToken(): OtpResponse

    // Banner
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanner(bannerList: List<BannerVO>)

    @Query("SELECT * FROM banner_table")
    fun getBanners(): List<BannerVO>

    // Movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movieList: List<MovieVO>)

    @Query("SELECT * FROM movie_table WHERE type = :type")
    fun getMoviesByType(type: String): List<MovieVO>

    // Movie Detail
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleMovie(movie: MovieVO?)

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    fun getMovieById(movieId: Int): MovieVO?

    // Cinema
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCinemaConfig(config: List<ConfigVO>)

    @Query("SELECT * FROM config_table WHERE key = :key")
    fun getCinemaConfig(key: String): ConfigVO?

    // Cinema Info
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCinemaInfo(cinemaInfo: List<CinemaInfoVO>)

    @Query("SELECT * FROM cinema_info_table WHERE id = :cinemaId")
    fun getCinemaInfo(cinemaId: Int): CinemaInfoVO?

    // Logout
    @Transaction
    @Query("DELETE FROM cities_table")
    fun deleteAllFromCitiesVO()

    @Transaction
    @Query("DELETE FROM otp_table")
    fun deleteAllFromOTP()

    @Transaction
    @Query("DELETE FROM banner_table")
    fun deleteAllFromBannerVO()

    @Transaction
    @Query("DELETE FROM movie_table")
    fun deleteAllFromMovieVO()

    @Transaction
    @Query("DELETE FROM config_table")
    fun deleteAllFromConfigVO()

    @Transaction
    @Query("DELETE FROM cinema_info_table")
    fun deleteAllFromCinemaInfoVO()

    @Transaction
    @Query("DELETE FROM ticket_table")
    fun deleteAllFromTicketInformation()

    @Transaction
    fun deleteAllEntities(){
        deleteAllFromOTP()
        deleteAllFromBannerVO()
        deleteAllFromMovieVO()
        deleteAllFromConfigVO()
        deleteAllFromCinemaInfoVO()
        deleteAllFromTicketInformation()
    }

    // Ticket
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket: TicketInformation)

    @Query("SELECT * FROM ticket_table")
    fun getAllTicket(): List<TicketInformation>

    @Query("DELETE FROM ticket_table WHERE id = :ticketId")
    fun deleteTicket(ticketId: Int)
}