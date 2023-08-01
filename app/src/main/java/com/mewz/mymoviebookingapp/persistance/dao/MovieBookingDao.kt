package com.mewz.mymoviebookingapp.persistance.dao

import androidx.room.*
import com.mewz.mymoviebookingapp.data.vos.cinema.CinemaInfoVO
import com.mewz.mymoviebookingapp.data.vos.login.CitiesVO
import com.mewz.mymoviebookingapp.data.vos.movie.BannerVO
import com.mewz.mymoviebookingapp.data.vos.movie.MovieVO
import com.mewz.mymoviebookingapp.data.vos.movie.cinema_showtime.CinemaConfigVO
import com.mewz.mymoviebookingapp.data.vos.ticket.TicketInformation
import com.mewz.mymoviebookingapp.network.responses.login.OtpResponse

@Dao
interface MovieBookingDao {
    // Cities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities:List<CitiesVO>)

    @Query("SELECT * FROM cities")
    fun getAllCities():List<CitiesVO>

    // Sign
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSignInInformation(otpList: OtpResponse?)

    @Query("SELECT * FROM OTP WHERE code = :code")
    fun getSignInInformation(code:Int): OtpResponse

    // Banner
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanner(banners:List<BannerVO>)

    @Query("SELECT * FROM banner")
    fun getBanners():List<BannerVO>

    // Movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies:List<MovieVO>)

    @Query("SELECT * FROM movie")
    fun getAllMovies():List<MovieVO>

    @Query("SELECT * FROM movie WHERE type = :type")
    fun getMoviesByType(type:String):List<MovieVO>

    // Movie Detail
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleMovie(movie: MovieVO?)

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovieById(movieId:Int):MovieVO?

    // Cinema
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCinemaConfig(config: List<CinemaConfigVO>)

    @Query("SELECT * FROM config WHERE key = :key")
    fun getCinemaConfig(key:String):CinemaConfigVO?

    // Cinema Info Screen
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCinemaInfo(config: List<CinemaInfoVO>)

    @Query("SELECT * FROM cinema_info WHERE id = :cinemaId")
    fun getCinemaInfo(cinemaId:Int):CinemaInfoVO?

    @Transaction
    @Query("DELETE FROM cities")
    fun deleteAllFromCitiesVO()

    @Transaction
    @Query("DELETE FROM OTP")
    fun deleteAllFromOTP()

    @Transaction
    @Query("DELETE FROM banner")
    fun deleteAllFromBannerVO()

    @Transaction
    @Query("DELETE FROM movie")
    fun deleteAllFromMovieVO()

    @Transaction
    @Query("DELETE FROM config")
    fun deleteAllFromConfigVO()

    @Transaction
    @Query("DELETE FROM cinema_info")
    fun deleteAllFromCinemaInfoVO()

    @Transaction
    @Query("DELETE FROM ticket")
    fun deleteAllFromTicketInformation()

    @Transaction
    fun deleteAll(){
        deleteAllFromCitiesVO()
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

    @Query("SELECT * FROM ticket")
    fun getAllTickets():List<TicketInformation>

    @Query("DELETE FROM ticket WHERE id = :ticketId")
    fun deleteTicket(ticketId:Int)
}